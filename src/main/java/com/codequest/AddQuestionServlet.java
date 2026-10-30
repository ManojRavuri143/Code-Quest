package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/add-question")
public class AddQuestionServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || session.getAttribute("adminId") == null) {
            response.sendRedirect(request.getContextPath() + "/admin-login.jsp");
            return;
        }
        request.setCharacterEncoding("UTF-8");
        String title = request.getParameter("title");
        String description = request.getParameter("description");
        String category = request.getParameter("category");
        String difficulty = request.getParameter("difficulty");
        String a = request.getParameter("option_a");
        String b = request.getParameter("option_b");
        String c = request.getParameter("option_c");
        String d = request.getParameter("option_d");
        String correct = request.getParameter("correct_answer");
        int xp;
        try { xp = Integer.parseInt(request.getParameter("xp_reward")); }
        catch (Exception ex) { xp = -1; }

        if (blank(title) || blank(description) || blank(a) || blank(b) || blank(c) || blank(d)
                || !("A".equals(correct) || "B".equals(correct) || "C".equals(correct) || "D".equals(correct))
                || xp < 1 || xp > 1000) {
            request.setAttribute("error", "Fill every field and enter an XP reward from 1 to 1000.");
            request.getRequestDispatcher("/add-question.jsp").forward(request, response);
            return;
        }
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(
                "INSERT INTO missions(title,description,category,difficulty,option_a,option_b,option_c,option_d,correct_answer,xp_reward) VALUES(?,?,?,?,?,?,?,?,?,?)")) {
            ps.setString(1, title.trim()); ps.setString(2, description.trim());
            ps.setString(3, category); ps.setString(4, difficulty);
            ps.setString(5, a.trim()); ps.setString(6, b.trim()); ps.setString(7, c.trim());
            ps.setString(8, d.trim()); ps.setString(9, correct); ps.setInt(10, xp);
            ps.executeUpdate();
            response.sendRedirect(request.getContextPath() + "/view-questions?added=1");
        } catch (SQLException e) {
            log("Adding question failed", e);
            request.setAttribute("error", "Could not save the question. Please try again.");
            request.getRequestDispatcher("/add-question.jsp").forward(request, response);
        }
    }
    private boolean blank(String s) { return s == null || s.trim().isEmpty(); }
}
