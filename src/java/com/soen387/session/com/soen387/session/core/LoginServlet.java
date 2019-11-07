package com.soen387.session.com.soen387.session.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.soen387.repository.com.soen387.repository.core.HashCheck;
import com.soen387.repository.com.soen387.repository.core.User;
import com.soen387.repository.com.soen387.repository.core.Session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;


/**
 *
 * @author Louis-Simon
 */
@WebServlet("/login")
public class LoginServlet extends BaseProtectedPage {
    
    // CONSULT user.JSON file to see user/pw
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        businessSession = new Session(request);
        PrintWriter out = response.getWriter();
        JSONObject JSONResponse = new JSONObject();
        JSONResponse.put("status", "failure");
        JSONResponse.put("message", "");
        if(!this.isLoggedIn(request)){
            String UserField = "user";
            String PassField = "pw";

            if(request.getParameter(UserField) != null && request.getParameter(PassField) != null){
                //Get request username & password
                String username = request.getParameter("user");
                String pw = request.getParameter("pw");

                if(businessSession.login(username, pw)){
                    JSONResponse.put("status", "success");
                    JSONResponse.put("message", "You have sucessfully logged in!");
                } else {
                    JSONResponse.put("message", "Invalid password");
                }
            } else {
                JSONResponse.put("message", "No credentials provided");
            }
        } else {
            JSONResponse.put("status", "success");
            JSONResponse.put("message", "your already logged in!");
        }
        
        response.setContentType("application/json"); 
        out.println(JSONResponse);
    }
    
    
    
     // CONSULT user.JSON file to see user/pw
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        businessSession = new Session(request);
        if(this.isLoggedIn(request)){
            response.sendRedirect("/home");
        } else {
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/publicPage.jsp"); 
            request.setAttribute("script", "login.js");

            // Output contents
            response.setContentType("text/html");
            view.include(request, response);
        }
    }
    
}
