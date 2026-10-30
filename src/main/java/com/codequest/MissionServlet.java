package com.codequest;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;
import java.util.*;

@WebServlet("/missions")
public class MissionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<Map<String,Object>> missions = new ArrayList<>();
        HttpSession session = request.getSession(false);
        Integer studentId = session != null && session.getAttribute("studentId") instanceof Integer
                ? (Integer) session.getAttribute("studentId") : null;
        String sql = "SELECT m.id,m.title,m.description,m.category,m.difficulty,m.option_a,m.option_b,m.option_c,m.option_d," +
                "m.xp_reward, CASE WHEN s.id IS NULL THEN 0 ELSE 1 END AS attempted " +
                "FROM missions m LEFT JOIN mission_submissions s ON m.id=s.mission_id AND s.user_id=? ORDER BY m.id";
        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, studentId == null ? -1 : studentId);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Map<String,Object> m = new HashMap<>();
                    m.put("id", rs.getInt("id")); m.put("title", rs.getString("title"));
                    m.put("description", rs.getString("description")); m.put("category", rs.getString("category"));
                    m.put("difficulty", rs.getString("difficulty")); m.put("a", rs.getString("option_a"));
                    m.put("b", rs.getString("option_b")); m.put("c", rs.getString("option_c"));
                    m.put("d", rs.getString("option_d")); m.put("xp", rs.getInt("xp_reward"));
                    m.put("attempted", rs.getInt("attempted") == 1); missions.add(m);
                }
            }
            request.setAttribute("missions", missions);
            request.getRequestDispatcher("/missions.jsp").forward(request, response);
        } catch (SQLException e) {
            log("Loading missions failed", e);
            request.setAttribute("error", "Could not load missions. Check database settings.");
            request.getRequestDispatcher("/missions.jsp").forward(request, response);
        }
    }
}
