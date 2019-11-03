package com.soen387.session.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.soen387.repository.core.AppConfig;
import com.soen387.repository.core.User;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.NoSuchAlgorithmException;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


import java.util.Enumeration;

import java.io.InputStream;
import java.io.StringWriter;
import java.io.InputStreamReader;

import java.util.HashMap;
import java.util.Map;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.*;

/**
 *
 * @author Louis-Simon
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    
    
    protected String getJSessionId(HttpServletRequest request){
        String jSessionId = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    jSessionId = cookie.getValue();
                    break;
                }
            }
        }
        return jSessionId;
    }
    
    public void logUserIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        String UserField = "user";
        String PassField = "pw";
       
        JSONObject JSONResponse = new JSONObject();
        JSONResponse.put("status", "failure");
        JSONResponse.put("message", "");
        
        if(request.getParameter(UserField) != null && request.getParameter(PassField) != null){
            //Get request username & password
            String username = request.getParameter("user");
            String pw = request.getParameter("pw");
            
            // Detect User
            User user = HashCheck.getMatchingUser(username, pw);
            
            if (user != null) {
                HttpSession session = request.getSession(true);
                session.setMaxInactiveInterval(30 * 60);
                session.setAttribute("user_id", user.getId());
                session.setAttribute("username", user.getUsername());    
                JSONResponse.put("status", "success");
                JSONResponse.put("message", "Yuo have sucessfully logged in!");
            } else {
                JSONResponse.put("message", "Invalid password");
            }
        } else {
            JSONResponse.put("message", "No credentials provided");
        }
        
        response.setContentType("application/json"); 
        out.println(JSONResponse);
    }
    
    
    // CONSULT user.JSON file to see user/pw
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logUserIn(request, response);
    }
    
    
     // CONSULT user.JSON file to see user/pw
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        logUserIn(request, response);
    }
    
}
