package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/register")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String username = request.getParameter("username");
        String email = request.getParameter("email");
        String password = request.getParameter("password");
        String confirm = request.getParameter("confirmPassword");

        if (blank(username) || blank(email) || blank(password) || blank(confirm)) {
            request.setAttribute("error", "Please fill in every field.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        if (!password.equals(confirm)) {
            request.setAttribute("error", "Passwords do not match.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }
        if (password.length() < 6) {
            request.setAttribute("error", "Password must have at least 6 characters.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            try (PreparedStatement check = con.prepareStatement(
                    "SELECT id FROM users WHERE username=? OR email=?")) {
                check.setString(1, username.trim());
                check.setString(2, email.trim());
                try (ResultSet rs = check.executeQuery()) {
                    if (rs.next()) {
                        request.setAttribute("error", "Username or email already exists.");
                        request.getRequestDispatcher("/register.jsp").forward(request, response);
                        return;
                    }
                }
            }
            try (PreparedStatement ps = con.prepareStatement(
                    "INSERT INTO users(username,email,password,xp,level) VALUES(?,?,?,0,1)")) {
                ps.setString(1, username.trim());
                ps.setString(2, email.trim());
                ps.setString(3, PasswordUtil.hash(password));
                ps.executeUpdate();
            }
            response.sendRedirect(request.getContextPath() + "/login.jsp?registered=1");
        } catch (SQLException e) {
            log("Registration failed", e);
            request.setAttribute("error", "Could not register right now. Check the database settings.");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
        }
    }
    private boolean blank(String s) { return s == null || s.trim().isEmpty(); }
}
