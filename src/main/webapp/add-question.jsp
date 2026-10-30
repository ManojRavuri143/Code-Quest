<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codequest.HtmlUtil" %>
<%
if (session.getAttribute("adminId") == null) { response.sendRedirect("admin-login.jsp"); return; }
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Add Question</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="admin-dashboard.jsp">Dashboard</a><a href="view-questions">View Questions</a><a href="admin-logout">Logout</a></nav>
<div class="form-card"><h1>Add a Mission</h1>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<form action="add-question" method="post">
<label>Question Title</label><input name="title" maxlength="100" required>
<label>Question Description</label><textarea name="description" required></textarea>
<label>Category</label><select name="category"><option>Java</option><option>SQL</option><option>DSA</option></select>
<label>Difficulty</label><select name="difficulty"><option>Easy</option><option>Medium</option><option>Hard</option></select>
<label>Option A</label><input name="option_a" required>
<label>Option B</label><input name="option_b" required>
<label>Option C</label><input name="option_c" required>
<label>Option D</label><input name="option_d" required>
<label>Correct Answer</label><select name="correct_answer"><option value="A">A</option><option value="B">B</option><option value="C">C</option><option value="D">D</option></select>
<label>XP Reward (1–1000)</label><input type="number" name="xp_reward" min="1" max="1000" value="10" required>
<button type="submit">Save Question</button></form></div></body></html>
