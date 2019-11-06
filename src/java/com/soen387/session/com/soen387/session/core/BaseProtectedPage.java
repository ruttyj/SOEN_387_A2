package com.soen387.session.com.soen387.session.core;

import com.soen387.repository.com.soen387.repository.core.SecurityContext;
import com.soen387.repository.com.soen387.repository.core.Session;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

/**
 *
 * @author Jordan Rutty
 */
public class BaseProtectedPage extends HttpServlet {
    
    Session businessSession = null;
    
    public BaseProtectedPage(){
        super();
    }
    
    
    protected boolean isLoggedIn(HttpServletRequest request){
        if(businessSession == null){
            businessSession = new Session(request);
        }
        return businessSession.isUserLoggedIn();
    }
    
    
    // Check to see if the user is logged in else redirect
    protected boolean checkLoggedIn(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!this.isLoggedIn(request)){
            response.sendRedirect("/login");
            return false;
        }
        return true;
    }
    
    
    protected boolean checkLoggedInResponse(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(!this.isLoggedIn(request)){
            response.setStatus(401); // Unauthorized
            return false;
        }
        return true;
    }
        
        
    
    public JSONObject getUserJSON(){
        HttpSession session = businessSession.getHttpSession();
        JSONObject result = null;
        if(session.getAttribute("user_id") != null){
            result = new JSONObject();
            result.put("id", session.getAttribute("user_id"));
            result.put("username", session.getAttribute("username"));
        }
        return result;
    }
    
    protected SecurityContext getSecurityContext(HttpServletRequest request){
        SecurityContext securityContext = null;
        if(this.isLoggedIn(request)){
            HttpSession session = request.getSession(true);
            securityContext = new SecurityContext();
            securityContext.setUserId((int)session.getAttribute("user_id"));
        }
        return securityContext;
    }
    
    public class baseProtectedPage {

    }
}
