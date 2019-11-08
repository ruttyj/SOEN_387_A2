package com.soen387.session.com.soen387.session.core;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/404")
public class ServletNotFound extends BaseProtectedPage {
    
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //Get the required view
        RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/404Page.jsp"); 

        // Output contents
        response.setContentType("text/html");
        view.include(request, response);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
}

