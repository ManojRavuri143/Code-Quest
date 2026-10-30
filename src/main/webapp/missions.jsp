<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.codequest.HtmlUtil" %>
<%
List<Map<String,Object>> missions = (List<Map<String,Object>>) request.getAttribute("missions");
boolean loggedIn = session.getAttribute("studentId") != null;
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Programming Missions</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="index.html">Home</a><% if (loggedIn) { %><a href="dashboard">Dashboard</a><a href="logout">Logout</a><% } else { %><a href="login.jsp">Student Login</a><% } %><a href="leaderboard">Leaderboard</a></nav>
<main class="page"><h1 class="page-title">Programming Missions</h1><p>Choose an answer. Each mission can be attempted once.</p>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<% if (!loggedIn) { %><div class="notice">You can view the questions, but <a href="login.jsp">log in</a> to submit answers and earn XP.</div><% } %>
<div class="mission-grid">
<% if (missions != null && !missions.isEmpty()) { for (Map<String,Object> m : missions) { boolean attempted = Boolean.TRUE.equals(m.get("attempted")); %>
<div class="card mission"><div><span class="tag"><%= HtmlUtil.escape(m.get("category")) %></span><span class="tag"><%= HtmlUtil.escape(m.get("difficulty")) %></span><span class="tag"><%= m.get("xp") %> XP</span></div>
<h2><%= HtmlUtil.escape(m.get("title")) %></h2><p><%= HtmlUtil.escape(m.get("description")) %></p>
<% if (attempted) { %><div class="notice">Already attempted</div>
<% } else if (loggedIn) { %>
<form action="submit-answer" method="post"><input type="hidden" name="missionId" value="<%= m.get("id") %>">
<div class="option"><input type="radio" id="a<%= m.get("id") %>" name="answer" value="A" required><label for="a<%= m.get("id") %>">A. <%= HtmlUtil.escape(m.get("a")) %></label></div>
<div class="option"><input type="radio" id="b<%= m.get("id") %>" name="answer" value="B"><label for="b<%= m.get("id") %>">B. <%= HtmlUtil.escape(m.get("b")) %></label></div>
<div class="option"><input type="radio" id="c<%= m.get("id") %>" name="answer" value="C"><label for="c<%= m.get("id") %>">C. <%= HtmlUtil.escape(m.get("c")) %></label></div>
<div class="option"><input type="radio" id="d<%= m.get("id") %>" name="answer" value="D"><label for="d<%= m.get("id") %>">D. <%= HtmlUtil.escape(m.get("d")) %></label></div>
<button type="submit">Submit Answer</button></form>
<% } else { %><p><a href="login.jsp">Log in</a> to answer this mission.</p><% } %></div>
<% } } else { %><div class="card">No missions available yet. The Admin can add questions after logging in.</div><% } %>
</div></main></body></html>
