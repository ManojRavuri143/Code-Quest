<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codequest.HtmlUtil" %>
<%
if (session.getAttribute("studentId") == null) { response.sendRedirect("login.jsp"); return; }
String message = (String) request.getAttribute("message");
Boolean correct = (Boolean) request.getAttribute("correct");
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Mission Result</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="dashboard">Dashboard</a><a href="missions">Missions</a><a href="leaderboard">Leaderboard</a><a href="logout">Logout</a></nav>
<main class="page center"><div class="form-card">
<% if (Boolean.TRUE.equals(correct)) { %><h1 class="page-title">Nice work!</h1><div class="success"><%= HtmlUtil.escape(message) %></div>
<% } else { %><h1 class="page-title">Mission Result</h1><div class="notice"><%= HtmlUtil.escape(message == null ? "No result to show." : message) %></div><% } %>
<p>Keep going and complete more missions.</p><a class="btn" href="missions">Back to Missions</a><a class="btn btn-outline" href="dashboard">My Dashboard</a></div></main></body></html>
