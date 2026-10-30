package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/view-questions")
public class ViewQuestionsServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
            return;
        }
        List<Map<String,Object>> questions = new ArrayList<>();
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                 "SELECT id,title,category,difficulty,xp_reward FROM missions ORDER BY id DESC");
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                Map<String,Object> row = new HashMap<>();
                row.put("id", rs.getInt("id")); row.put("title", rs.getString("title"));
                row.put("category", rs.getString("category")); row.put("difficulty", rs.getString("difficulty"));
                row.put("xp", rs.getInt("xp_reward")); questions.add(row);
            }
            request.setAttribute("questions", questions);
            request.getRequestDispatcher("/view-questions.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Loading questions failed", e);
            request.setAttribute("error", "Could not load questions from the database.");
            request.getRequestDispatcher("/view-questions.jsp").forward(request, response);
        }
    }
}
