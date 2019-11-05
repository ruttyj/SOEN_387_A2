package com.soen387.session.core;

import com.soen387.repository.core.SecurityContext;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

/**
 *
 * @author Jordan Rutty
 */
public class BaseProtectedPage extends HttpServlet {
    
    
    
    protected boolean isLoggedIn(HttpSession session){
        return session != null && session.getAttribute("user_id") != null;
    }
    
    
    // Check to see if the user is logged in else redirect
    protected boolean checkLoggedIn(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        if(!this.isLoggedIn(session)){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
    
    
    protected boolean checkLoggedInResponse(HttpSession session, HttpServletResponse response) throws ServletException, IOException {
        if(!this.isLoggedIn(session)){
            response.setStatus(401); // Unauthorized
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
    
    protected SecurityContext getSecurityContext(HttpSession session){
        SecurityContext securityContext = null;
        if(this.isLoggedIn(session)){
            securityContext = new SecurityContext();
            securityContext.setUserId((int)session.getAttribute("user_id"));
        }
        return securityContext;
    }
    
    public class baseProtectedPage {

    }
}
