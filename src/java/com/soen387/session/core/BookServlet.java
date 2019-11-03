package com.soen387.session.core;

import com.soen387.repository.core.IBookRepository;
import com.soen387.repository.core.BookRepository;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Enumeration;
import java.io.*;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/books")
public class BookServlet extends BaseProtectedPage {
    
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(this.checkLoggedIn(session, response)){
            PrintWriter out = response.getWriter();
            
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/singlePage.jsp"); 
            
            // Get page data
            JSONObject initalData = new JSONObject();
            initalData.put("userData", getUserJSON(session));
            
            IBookRepository bookRepo = BookRepository.getInstance("context");
            
            // Inject data into view
            request.setAttribute("initalData", initalData.toString());
            request.setAttribute("script", "home.js");
            
            // Output contents
            response.setContentType("text/html");
            view.include(request, response);
        } 
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
}

