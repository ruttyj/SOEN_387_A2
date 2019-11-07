/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author Jordan Rutty
 */

public class Session {
    
    private User currentUser = null;
    private HttpSession session = null;
    private String JSessionId = null;
    private HttpServletRequest request = null;
    
    
    public Session(){
        
    }
    
    
    public Session(HttpServletRequest request){
        session = request.getSession(true);
        JSessionId = findJSessionId(request);
        this.request = request;
        detectLoggedInUser();
    }
    
    public User getCurrentUser(){
        return this.currentUser;
    }
    
    public HttpSession getHttpSession(){
        return session;
    }
    
    protected void detectLoggedInUser(){
        if(this.currentUser == null){
            if(session != null && this.request.isRequestedSessionIdValid() && session.getAttribute("user_id") != null){
                this.currentUser = new User();
                this.currentUser.setId(Integer.parseInt(session.getAttribute("user_id").toString()));
                this.currentUser.setUsername(session.getAttribute("username").toString());
            } else {
                this.currentUser = null;
            }
        }
    }
    
    public boolean isUserLoggedIn(){
        this.detectLoggedInUser();
        return currentUser != null;
    }
    
    public boolean login(String username, String password){
        User user = HashCheck.getMatchingUser(username, password);
        if (user != null) {
            session.setMaxInactiveInterval(30 * 60);
            session.setAttribute("user_id", user.getId());
            session.setAttribute("username", user.getUsername()); 
            detectLoggedInUser();
            return true;
        }
        return false;
    }
    
    
    protected String findJSessionId(HttpServletRequest request){
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
    
    
    public void logout(){
        this.currentUser = null;
        
        HttpSession session = request.getSession();
        if (session != null) {
            session.invalidate();
            Cookie sessionCookie = this.findSessionCookie(request);
            if(sessionCookie != null){
                sessionCookie.setMaxAge(0);
                System.out.println("setMaxAge 0 ");
            }
        }
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
    
    
}
