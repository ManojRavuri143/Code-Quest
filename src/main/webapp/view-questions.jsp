<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="java.util.*,com.codequest.HtmlUtil" %>
<%
if (session.getAttribute("adminId") == null) { response.sendRedirect("admin-login.jsp"); return; }
List<Map<String,Object>> questions = (List<Map<String,Object>>) request.getAttribute("questions");
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>View Questions</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="admin-dashboard.jsp">Dashboard</a><a href="add-question.jsp">Add Question</a><a href="admin-logout">Logout</a></nav>
<main class="page"><h1 class="page-title">Question Management</h1>
<% if ("1".equals(request.getParameter("added"))) { %><div class="success">Question added successfully.</div><% } %>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<div class="table-wrap"><table><thead><tr><th>ID</th><th>Title</th><th>Category</th><th>Difficulty</th><th>XP</th></tr></thead><tbody>
<% if (questions != null && !questions.isEmpty()) { for (Map<String,Object> q : questions) { %>
<tr><td><%= q.get("id") %></td><td><%= HtmlUtil.escape(q.get("title")) %></td><td><%= HtmlUtil.escape(q.get("category")) %></td><td><%= HtmlUtil.escape(q.get("difficulty")) %></td><td><%= q.get("xp") %></td></tr>
<% } } else { %><tr><td colspan="5">No questions found. Add your first mission.</td></tr><% } %>
</tbody></table></div></main></body></html>
