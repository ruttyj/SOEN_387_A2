package com.soen387.session.core;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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

/**
 *
 * @author Louis-Simon
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    // CONSULT user.JSON file to see user/pw
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        //Get request username & password
        String username = request.getParameter("user");
        String pw = request.getParameter("pw");

        /*  HashCheck class has two methods
            - ComputeMD5 to hash the password input
            - CompareHashes to compare the hashed input with the JSON password for that user
         */
        HashCheck hc = new HashCheck(username, pw);

        try {
            if (hc.compareHashes(hc.getUsername(), hc.computeMD5(pw))) {

                HttpSession session = request.getSession();
                session.setAttribute("user", username);
                session.setMaxInactiveInterval(30 * 60);

                Cookie userName = new Cookie("user", hc.getUsername());
                userName.setMaxAge(30 * 60);
                response.addCookie(userName);
                response.sendRedirect("LoginSuccess.jsp");

            } else {

                RequestDispatcher rd = getServletContext().getRequestDispatcher("/logintest.html");
                PrintWriter out = response.getWriter();
                out.println("<font color=red>Either username or password is wrong.</font>");
                rd.include(request, response);
            }
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
