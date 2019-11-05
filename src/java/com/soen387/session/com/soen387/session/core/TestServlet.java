package com.soen387.session.com.soen387.session.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.soen387.repository.com.soen387.repository.core.AppConfig;
import com.soen387.repository.com.soen387.repository.core.User;
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

import java.util.Enumeration;
import java.io.*;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/TestServlet")
public class TestServlet extends HttpServlet {
    
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
    
    
    
    
    public void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = response.getWriter();
        
        HttpSession session = request.getSession(false);
        
        if(session != null){
            out.println("session id: " + session.getId());   
            
            
            Enumeration attrNames = session.getAttributeNames();
            while(attrNames.hasMoreElements()) {
                String current = (String) attrNames.nextElement();
                out.println(current + "=" + session.getAttribute(current));            
            } 
        }
        
        
        out.println("end");
    }
    
    
    // CONSULT user.JSON file to see user/pw
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }
    
    
     // CONSULT user.JSON file to see user/pw
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }
    
}

