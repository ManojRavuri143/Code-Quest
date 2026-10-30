<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codequest.HtmlUtil" %>
<%
if (session.getAttribute("adminId") == null) {
    response.sendRedirect("admin-login.jsp");
    return;
}
String adminName = (String) session.getAttribute("adminUsername");
%>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Admin Dashboard</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="index.html">Home</a><a href="add-question.jsp">Add Question</a><a href="view-questions">View Questions</a><a href="admin-logout">Logout</a></nav>
<main class="page"><h1 class="page-title">Admin Dashboard</h1><p>Welcome, <%= HtmlUtil.escape(adminName) %>. Manage the programming questions here.</p>
<div class="feature-grid"><div class="feature"><h3>Add Questions</h3><p>Create Java, SQL and DSA missions.</p><a class="btn" href="add-question.jsp">Add Question</a></div>
<div class="feature"><h3>View Questions</h3><p>See questions currently saved in the database.</p><a class="btn" href="view-questions">View Questions</a></div></div></main></body></html>
