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

import java.io.InputStream;
import java.io.StringWriter;
import java.io.InputStreamReader;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.Enumeration;
import java.io.*;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/home")
public class HomeServlet extends HttpServlet {
    
    protected String getSessionId(HttpServletRequest request){
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
    
    // Check to see if the user is logged in else redirect
    protected boolean checkLogin(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        if(session == null || session.getAttribute("user_id") == null){
            response.sendRedirect("login.html");
            return false;
        }
        return true;
    }
    
    public JSONObject getUserJSON(HttpSession session){
        JSONObject result = null;
        if(session.getAttribute("user_id") != null){
            result = new JSONObject();
            result.put("id", session.getAttribute("user_id"));
            result.put("username", session.getAttribute("username"));
        }
        return result;
    }
    
    
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(this.checkLogin(session, response)){
            PrintWriter out = response.getWriter();
            
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/singlePage.jsp"); 
            
            // Get page data
            JSONObject initalData = new JSONObject();
            initalData.put("userData", getUserJSON(session));
            
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

