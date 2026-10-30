<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.codequest.HtmlUtil" %>
<%
List<Map<String,Object>> players = (List<Map<String,Object>>) request.getAttribute("players");
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><meta http-equiv="refresh" content="30"><title>Leaderboard - CodeQuest</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="index.html">Home</a><a href="missions">Missions</a><a href="login.jsp">Student Login</a></nav>
<main class="page"><h1 class="page-title">Leaderboard</h1><p>Students are ranked by total XP. This page refreshes every 30 seconds.</p>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<div class="table-wrap"><table><thead><tr><th>Rank</th><th>Student</th><th>Level</th><th>Completed Missions</th><th>Total XP</th></tr></thead><tbody>
<% if (players != null && !players.isEmpty()) { for (Map<String,Object> p : players) { int rank = (Integer)p.get("rank"); %>
<tr><td class="<%= rank <= 3 ? "top-rank" : "" %>"><%= rank == 1 ? "🥇 1" : rank == 2 ? "🥈 2" : rank == 3 ? "🥉 3" : rank %></td>
<td><%= HtmlUtil.escape(p.get("username")) %></td><td><%= p.get("level") %></td><td><%= p.get("completed") %></td><td><strong><%= p.get("xp") %></strong></td></tr>
<% } } else { %><tr><td colspan="5">No players yet. Register and be the first!</td></tr><% } %>
</tbody></table></div></main></body></html>
