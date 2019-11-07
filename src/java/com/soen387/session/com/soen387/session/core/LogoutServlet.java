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
public class LogoutServlet extends HttpServlet {

    protected void doAll(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session businessSession = new Session();
        businessSession.logout(request);
        response.sendRedirect("/logout.html");
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }
    
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAll(request, response);
    }

}
