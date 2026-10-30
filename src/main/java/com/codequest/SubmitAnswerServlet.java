package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet("/submit-answer")
public class SubmitAnswerServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession(false);
        if (session == null || !(session.getAttribute("studentId") instanceof Integer)) {
            response.sendRedirect(request.getContextPath() + "/login.jsp");
            return;
        }
        int userId = (Integer) session.getAttribute("studentId");
        int missionId;
        String answer = request.getParameter("answer");
        try { missionId = Integer.parseInt(request.getParameter("missionId")); }
        catch (Exception e) {
            request.setAttribute("message", "Invalid mission.");
            request.getRequestDispatcher("/mission-result.jsp").forward(request, response);
            return;
        }
        if (!("A".equals(answer) || "B".equals(answer) || "C".equals(answer) || "D".equals(answer))) {
            request.setAttribute("message", "Please choose an answer.");
            request.getRequestDispatcher("/mission-result.jsp").forward(request, response);
            return;
        }

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);
            try {
                String correct;
                int reward;
                try (PreparedStatement q = con.prepareStatement(
                        "SELECT correct_answer,xp_reward FROM missions WHERE id=? FOR UPDATE")) {
                    q.setInt(1, missionId);
                    try (ResultSet rs = q.executeQuery()) {
                        if (!rs.next()) throw new SQLException("Mission not found");
                        correct = rs.getString("correct_answer");
                        reward = rs.getInt("xp_reward");
                    }
                }
                boolean isCorrect = answer.equals(correct);
                try (PreparedStatement check = con.prepareStatement(
                        "SELECT id FROM mission_submissions WHERE user_id=? AND mission_id=?")) {
                    check.setInt(1, userId); check.setInt(2, missionId);
                    try (ResultSet rs = check.executeQuery()) {
                        if (rs.next()) {
                            con.rollback();
                            request.setAttribute("message", "You have already attempted this mission. Only one attempt is allowed.");
                            request.getRequestDispatcher("/mission-result.jsp").forward(request, response);
                            return;
                        }
                    }
                }
                try (PreparedStatement insert = con.prepareStatement(
                        "INSERT INTO mission_submissions(user_id,mission_id,answer,is_correct) VALUES(?,?,?,?)")) {
                    insert.setInt(1, userId); insert.setInt(2, missionId);
                    insert.setString(3, answer); insert.setBoolean(4, isCorrect); insert.executeUpdate();
                }
                if (isCorrect) {
                    try (PreparedStatement update = con.prepareStatement(
                            "UPDATE users SET xp=xp+?, level=FLOOR((xp+?)/100)+1 WHERE id=?")) {
                        update.setInt(1, reward); update.setInt(2, reward); update.setInt(3, userId);
                        update.executeUpdate();
                    }
                    request.setAttribute("message", "Correct answer! You earned " + reward + " XP.");
                    request.setAttribute("correct", Boolean.TRUE);
                } else {
                    request.setAttribute("message", "Not quite. No XP was awarded. You can try another mission.");
                    request.setAttribute("correct", Boolean.FALSE);
                }
                con.commit();
            } catch (SQLException e) {
                con.rollback();
                if (e.getErrorCode() == 1062) {
                    request.setAttribute("message", "You have already attempted this mission.");
                } else {
                    throw e;
                }
            } finally {
                con.setAutoCommit(true);
            }
            request.getRequestDispatcher("/mission-result.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Answer submission failed", e);
            request.setAttribute("message", "Could not submit your answer. Please try again.");
            request.getRequestDispatcher("/mission-result.jsp").forward(request, response);
        }
    }
}
