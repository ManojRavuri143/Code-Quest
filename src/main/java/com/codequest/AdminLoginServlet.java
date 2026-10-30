package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/admin-login")
public class AdminLoginServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        getServletContext().setAttribute("appName", "CodeQuest");
        getServletConfig().getInitParameter("unused"); // Demonstrates ServletConfig access.
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null) {
            request.setAttribute("error", "Enter admin username and password.");
            request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
            return;
        }
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id,username FROM admins WHERE username=? AND password=?")) {
            ps.setString(1, username.trim());
            ps.setString(2, PasswordUtil.hash(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HttpSession old = request.getSession(false);
                    if (old != null) old.invalidate();
                    HttpSession session = request.getSession(true);
                    session.setAttribute("adminId", rs.getInt("id"));
                    session.setAttribute("adminUsername", rs.getString("username"));
                    response.sendRedirect(request.getContextPath() + "/admin-dashboard.jsp");
                    return;
                }
            }
            request.setAttribute("error", "Incorrect admin username or password.");
            request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Admin login failed", e);
            request.setAttribute("error", "Could not log in. Check database settings.");
            request.getRequestDispatcher("/admin-login.jsp").forward(request, response);
        }
    }
}
