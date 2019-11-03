package com.soen387.session.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Louis-Simon
 */
@WebServlet(urlPatterns = {"/logout"})
public class LogoutServlet extends HttpServlet {

//    /**
//     * Handles the HTTP <code>POST</code> method.
//     *
//     * @param request servlet request
//     * @param response servlet response
//     * @throws ServletException if a servlet-specific error occurs
//     * @throws IOException if an I/O error occurs
//     */
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//        
//        response.setContentType("text/html");
//        Cookie loginCookie = null;
//        Cookie[] cookies = request.getCookies();
//        if (cookies != null) {
//            for (Cookie cookie : cookies) {
//                if(cookie.getName().equals("user")){
//    			loginCookie = cookie;
//    			break;
//    		}
//            }
//        }
//        if(loginCookie != null){
//    		loginCookie.setMaxAge(0);
//        	response.addCookie(loginCookie);
//    	}
//    	response.sendRedirect("login.html");
//    }
    
    protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        
        //invalidate the session if exists
        HttpSession session = request.getSession(true);
        System.out.println("User=" + session.getAttribute("user"));
        if (session != null) {
            session.invalidate();
        }
        
        Cookie sessionCookie = getSessionCookie(request);
        if(sessionCookie != null){
            sessionCookie.setMaxAge(0);
        }
        
        response.sendRedirect("home");
    }
    
    
    protected Cookie getSessionCookie(HttpServletRequest request){
        Cookie sessionCookie = null;
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if (cookie.getName().equals("JSESSIONID")) {
                    sessionCookie = cookie;
                    break;
                }
            }
        }
        return sessionCookie;
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }

}
