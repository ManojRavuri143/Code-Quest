package com.codequest;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet("/app-info")
public class ServletInfoServlet extends HttpServlet {
    @Override
    public void init() throws ServletException {
        String appName = getServletContext().getInitParameter("appName");
        if (appName != null) getServletContext().setAttribute("appName", appName);
        String label = getServletConfig().getInitParameter("label");
        if (label != null) getServletContext().setAttribute("servletLabel", label);
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain;charset=UTF-8");
        response.getWriter().println("Application: " + getServletContext().getAttribute("appName"));
        response.getWriter().println("Servlet label: " + getServletContext().getAttribute("servletLabel"));
    }
}
