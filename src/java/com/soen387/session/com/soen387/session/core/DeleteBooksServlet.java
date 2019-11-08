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
@WebServlet("/deleteBooks")
public class DeleteBooksServlet extends BaseProtectedPage {
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Session sessionBean = this.getSessionBean(request);
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        result.put("status", "failure");
        result.put("message", "");
           
        if(this.checkLoggedInResponse(request, response)){
            // Collect Ids into ArrayList
            ArrayList<Integer> deleteIds = new ArrayList<Integer>();
            if(request.getParameter("ids[]") != null){
                String[] paramValues = request.getParameterMap().get("ids[]");
                for (String paramValue : paramValues) {
                    int id = Integer.parseInt(paramValue);
                    if(id != 0){
                        deleteIds.add(id);
                    }
                }
            }
            
            try {
                if(deleteIds.size() > 0){
                    IBookRepository bookRepo = BookRepository.getInstance(this.getSecurityContext(request));

                    for(int i=0; i < deleteIds.size(); ++i){
                        bookRepo.deleteBook(sessionBean, deleteIds.get(i));
                    }
                    result.put("status", "success");
                    result.put("message", "Sucessfully Deleted " + deleteIds.size() + " " + (deleteIds.size() == 1 ? "item" : "items") + ".");
                }
            } catch( Exception ex){
                Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
        }
        response.setContentType("application/json");
        out.println(result);
        out.close();
    }
    
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
}

