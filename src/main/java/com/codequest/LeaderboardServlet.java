package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/leaderboard")
public class LeaderboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String,Object>> players = new ArrayList<>();
        String sql = "SELECT u.username,u.level,u.xp," +
            "(SELECT COUNT(*) FROM mission_submissions s WHERE s.user_id=u.id AND s.is_correct=1) completed " +
            "FROM users u ORDER BY u.xp DESC,u.username ASC";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            int rank = 1;
            while (rs.next()) {
                Map<String,Object> p = new HashMap<>();
                p.put("rank", rank++); p.put("username", rs.getString("username"));
                p.put("level", rs.getInt("level")); p.put("xp", rs.getInt("xp"));
                p.put("completed", rs.getInt("completed")); players.add(p);
            }
            request.setAttribute("players", players);
            request.getRequestDispatcher("/leaderboard.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Leaderboard load failed", e);
            request.setAttribute("error", "Could not load the leaderboard.");
            request.getRequestDispatcher("/leaderboard.jsp").forward(request, response);
        }
    }
}
