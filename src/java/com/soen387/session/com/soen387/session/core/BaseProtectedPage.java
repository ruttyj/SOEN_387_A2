package com.soen387.session.com.soen387.session.core;

import com.soen387.repository.com.soen387.repository.core.Session;
import com.soen387.repository.com.soen387.repository.core.User;
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
    
    
    public BaseProtectedPage(){
        super();
    }
    
    protected Session getSessionBean(HttpServletRequest request){
        Session sessionBean = null;
        HttpSession httpSession = request.getSession();
        if(httpSession != null){
            if(httpSession.getAttribute("sessionBean") != null)
                sessionBean = (Session)httpSession.getAttribute("sessionBean");
        }
        
        
        
        return sessionBean;
    }
    
    protected boolean isLoggedIn(HttpServletRequest request){
        Session sessionBean = getSessionBean(request);
        return sessionBean == null ? false : sessionBean.isUserLoggedIn();
    }
    
    
    // Check to see if the user is logged in else redirect
    protected boolean checkLoggedInPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        
        
    
    public JSONObject getUserJSON(HttpServletRequest request){
        Session sessionBean = getSessionBean(request);
        JSONObject result = null;
        if(sessionBean != null){
            User user = sessionBean.getCurrentUser();
            if(user != null){
                result = new JSONObject();
                result.put("id", user.getId());
                result.put("username", user.getUsername());
            }
        }
        return result;
    }
   
    
    public class baseProtectedPage {

    }
}
