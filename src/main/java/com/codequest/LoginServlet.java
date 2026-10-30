package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String identity = request.getParameter("username");
        String password = request.getParameter("password");
        if (identity == null || password == null || identity.trim().isEmpty() || password.isEmpty()) {
            request.setAttribute("error", "Enter your username/email and password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
            return;
        }
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id,username FROM users WHERE (username=? OR email=?) AND password=?")) {
            ps.setString(1, identity.trim());
            ps.setString(2, identity.trim());
            ps.setString(3, PasswordUtil.hash(password));
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    HttpSession old = request.getSession(false);
                    if (old != null) old.invalidate();
                    HttpSession session = request.getSession(true);
                    session.setAttribute("studentId", rs.getInt("id"));
                    session.setAttribute("studentUsername", rs.getString("username"));
                    session.setMaxInactiveInterval(30 * 60);
                    Cookie cookie = new Cookie("cqLastUser", rs.getString("username"));
                    cookie.setMaxAge(7 * 24 * 60 * 60);
                    cookie.setHttpOnly(true);
                    cookie.setPath(request.getContextPath().isEmpty() ? "/" : request.getContextPath());
                    response.addCookie(cookie);
                    response.sendRedirect(request.getContextPath() + "/dashboard");
                    return;
                }
            }
            request.setAttribute("error", "Incorrect username/email or password.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Login failed", e);
            request.setAttribute("error", "Could not log in. Check the database connection.");
            request.getRequestDispatcher("/login.jsp").forward(request, response);
        }
    }
}
