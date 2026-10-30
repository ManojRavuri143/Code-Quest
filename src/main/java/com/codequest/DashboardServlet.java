package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/dashboard")
public class DashboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("studentId") == null) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        int id = (Integer) session.getAttribute("studentId");
        try (Connection con = DBConnection.getConnection();
             PreparedStatement user = con.prepareStatement(
                "SELECT username,xp,level FROM users WHERE id=?")) {
            user.setInt(1, id);
            try (ResultSet rs = user.executeQuery()) {
                if (!rs.next()) {
                    session.invalidate();
                    response.sendRedirect(request.getContextPath() + "/login.jsp");
                    return;
                }
                request.setAttribute("username", rs.getString("username"));
                request.setAttribute("xp", rs.getInt("xp"));
                request.setAttribute("level", rs.getInt("level"));
            }
            try (PreparedStatement stats = con.prepareStatement(
                "SELECT COUNT(*) FROM mission_submissions WHERE user_id=? AND is_correct=1")) {
                stats.setInt(1, id);
                try (ResultSet rs = stats.executeQuery()) {
                    rs.next(); request.setAttribute("completed", rs.getInt(1));
                }
            }
            int xp = (Integer) request.getAttribute("xp");
            request.setAttribute("progress", xp % 100);
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Dashboard load failed", e);
            request.setAttribute("error", "Could not load your progress. Please try again.");
            request.getRequestDispatcher("/dashboard.jsp").forward(request, response);
        }
    }
}
