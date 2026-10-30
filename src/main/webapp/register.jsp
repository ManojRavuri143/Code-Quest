<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ page import="com.codequest.HtmlUtil" %>
<!DOCTYPE html><html><head><meta charset="UTF-8"><meta name="viewport" content="width=device-width, initial-scale=1"><title>Register - CodeQuest</title><link rel="stylesheet" href="css/style.css"></head><body>
<nav><a class="brand" href="index.html">CodeQuest</a><a href="index.html">Home</a><a href="login.jsp">Student Login</a></nav>
<div class="form-card"><h1>Create Account</h1><p>Register to start your programming missions.</p>
<% if (request.getAttribute("error") != null) { %><div class="error"><%= HtmlUtil.escape(request.getAttribute("error")) %></div><% } %>
<form action="register" method="post">
<label>Username</label><input name="username" maxlength="50" required>
<label>Email</label><input type="email" name="email" maxlength="100" required>
<label>Password (minimum 6 characters)</label><input type="password" name="password" minlength="6" required>
<label>Confirm Password</label><input type="password" name="confirmPassword" minlength="6" required>
<button type="submit">Register</button>
</form><p>Already have an account? <a href="login.jsp">Login here</a></p></div></body></html>
