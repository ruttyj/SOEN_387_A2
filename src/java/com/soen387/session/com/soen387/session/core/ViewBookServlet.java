package com.soen387.session.com.soen387.session.core;

import com.soen387.repository.com.soen387.repository.core.JsonResourceFactory;
import com.soen387.repository.com.soen387.repository.core.IBookRepository;
import com.soen387.repository.com.soen387.repository.core.BookRepository;
import com.soen387.repository.com.soen387.repository.core.Book;
import com.soen387.repository.com.soen387.repository.core.Author;
import com.soen387.repository.com.soen387.repository.core.Publisher;
import com.soen387.repository.com.soen387.repository.core.Session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.util.ArrayList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/viewBook")
public class ViewBookServlet extends BaseProtectedPage {
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session sessionBean = this.getSessionBean(request);
        if(this.checkLoggedInPage(request, response)){
            
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/protectedPage.jsp"); 
            
            // Get page data
            JSONObject initalData = new JSONObject();
            initalData.put("userData", getUserJSON(request));
            
            
            JSONObject pageData = new JSONObject();
            if(request.getParameter("id") != null){
                int bookID = Integer.parseInt(request.getParameter("id"));
                IBookRepository bookRepo = BookRepository.getInstance(this.getSecurityContext(request));
                Book book = null;
                
                try {
                    book = bookRepo.getBookInfo(sessionBean, bookID);
                } catch( Exception ex){
                    Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                // Display Page
                if(book != null){
                    pageData.put("book", JsonResourceFactory.makeBookResource(book));
                } else {
                    pageData.put("message", "No Book Found");
                }
            } else {
                pageData.put("message", "No Book Specified");
            }
            initalData.put("pageData", pageData);

            // Inject data into view
            request.setAttribute("initalData", initalData.toString());
            request.setAttribute("script", "viewBook.js");

            // Output contents
            response.setContentType("text/html");
            view.include(request, response);
        } 
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
}

