/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.session.core;
import com.soen387.repository.core.User;
import com.soen387.repository.core.AppConfig;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

/**
 *
 * @author ruttyj
 */
public class AppSession {
    
    
    private static AppSession instance = null;
    private HttpServletRequest request = null;
    private HttpSession session = null;
    
    private AppSession(HttpServletRequest request){
        this.request = request;
        this.session = request.getSession(true);
        this.session.setMaxInactiveInterval(30 * 60);
    }
    
    public static AppSession getInstance(HttpServletRequest request){
        if(instance == null){
            synchronized (AppSession.class) {
                instance = new AppSession(request);
            }
        }
        return instance;
    }
    
    
    public void login(User user){
        this.session.setAttribute("username", user.getUsername());
        this.session.setAttribute("user_id", user.getId());
    }
    
    public JSONObject getUserJSON(){
        JSONObject result = null;
        if(this.session.getAttribute("user_id") != null){
            result = new JSONObject();
            result.put("id", this.session.getAttribute("user_id"));
            result.put("username", this.session.getAttribute("username"));
        }
        return result;
    }
    
    public void logout(){
        
    }
    
}
