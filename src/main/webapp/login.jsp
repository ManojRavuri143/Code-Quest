<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codequest.HtmlUtil" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Student Login - CodeQuest</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="index.html">Home</a><a href="register.jsp">Register</a></nav>
<div class="form-card"><h1>Student Login</h1>
<% if ("1".equals(request.getParameter("registered"))) { %><div class="success">Registration successful. Please log in.</div><% } %>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<form action="login" method="post"><label>Username or Email</label><input name="username" required>
<label>Password</label><input type="password" name="password" required><button type="submit">Login</button></form>
<p>New here? <a href="register.jsp">Create an account</a></p></div></body></html>
