<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codequest.HtmlUtil" %>
<%
if (session.getAttribute("studentId") == null) { response.sendRedirect("login.jsp"); return; }
String username = (String) request.getAttribute("username");
Integer xp = (Integer) request.getAttribute("xp");
Integer level = (Integer) request.getAttribute("level");
Integer completed = (Integer) request.getAttribute("completed");
Integer progress = (Integer) request.getAttribute("progress");
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Student Dashboard</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="dashboard">Dashboard</a><a href="missions">Missions</a><a href="leaderboard">Leaderboard</a><a href="logout">Logout</a></nav>
<main class="page"><h1 class="page-title">Hey, <%= HtmlUtil.escape(username) %>!</h1><p>Welcome to your learning dashboard. Keep practising and collect XP.</p>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<div class="stat-grid"><div class="card stat"><span>Total XP</span><strong><%= xp == null ? 0 : xp %></strong></div>
<div class="card stat"><span>Current Level</span><strong><%= level == null ? 1 : level %></strong></div>
<div class="card stat"><span>Missions Completed</span><strong><%= completed == null ? 0 : completed %></strong></div></div>
<h3>Progress to next level</h3><p><%= progress == null ? 0 : progress %> / 100 XP</p><div class="progress"><div style="width:<%= progress == null ? 0 : progress %>%"></div></div>
<div class="actions"><a class="btn" href="missions">Start a Mission</a><a class="btn btn-outline" href="leaderboard">View Leaderboard</a></div></main></body></html>
