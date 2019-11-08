package com.soen387.session.com.soen387.session.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import com.soen387.repository.com.soen387.repository.core.Session;

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
public class LogoutServlet extends BaseProtectedPage {

    protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        HttpSession httpSession = request.getSession(true);
        Session sessionBean = (Session)httpSession.getAttribute("sessionBean");
        if(sessionBean != null){
            if(sessionBean.isUserLoggedIn()){
                sessionBean.logout();
                if (httpSession != null) {
                    httpSession.invalidate();
                    Cookie sessionCookie = this.findSessionCookie(request);
                    if(sessionCookie != null){
                        sessionCookie.setMaxAge(0);
                        System.out.println("setMaxAge 0 ");
                    }
                }
            }
        }
        
        response.sendRedirect("/logout.html");
    }
    
  
    
    protected Cookie findSessionCookie(HttpServletRequest request){
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
