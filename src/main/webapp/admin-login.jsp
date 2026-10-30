<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codequest.HtmlUtil" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Admin Login - CodeQuest</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="index.html">Home</a></nav>
<div class="form-card"><h1>Admin Login</h1><p>For the project administrator.</p>
<% if ("1".equals(request.getParameter("logout"))) { %><div class="success">You have logged out.</div><% } %>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<form action="admin-login" method="post"><label>Admin Username</label><input name="username" required>
<label>Password</label><input type="password" name="password" required><button type="submit">Admin Login</button></form>
<p class="small">Demo login: admin / admin123</p></div></body></html>
