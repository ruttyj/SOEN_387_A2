package com.soen387.session.core;

import com.soen387.repository.core.JsonResourceFactory;
import com.soen387.repository.core.IBookRepository;
import com.soen387.repository.core.BookRepository;
import com.soen387.repository.core.Book;
import com.soen387.repository.core.Author;
import com.soen387.repository.core.Publisher;

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

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/addBook")
public class AddBookServlet extends BaseProtectedPage {
    
    public void doDisplayBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(this.checkLoggedIn(session, response)){
            PrintWriter out = response.getWriter();
            
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/singlePage.jsp"); 
            
            // Get page data
            JSONObject initalData = new JSONObject();
            initalData.put("userData", getUserJSON(session));
            
            JSONObject pageData = new JSONObject();
            initalData.put("pageData", pageData);

            // Inject data into view
            request.setAttribute("initalData", initalData.toString());
            request.setAttribute("script", "addBook.js");

            // Output contents
            response.setContentType("text/html");
            view.include(request, response);
        } 
    }
    
    
    public void doAddBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        result.put("status", "failure");
        result.put("message", "");
           
        if(this.checkLoggedInResponse(session, response)){
            // Collect Ids into ArrayList
           
            
            IBookRepository bookRepo = BookRepository.getInstance(this.getSecurityContext(session));

            String f;
            
            f = "title";
            String bookTitle = request.getParameter(f) != null ? request.getParameter(f) : "";
            
            f = "description";
            String bookDescription = request.getParameter(f) != null ? request.getParameter(f) : "";
            
            f = "isbn";
            String bookIsbn = request.getParameter(f) != null ? request.getParameter(f) : "";
            
            f = "authorFirstName";
            String bookAuthorFirstName = request.getParameter(f) != null ? request.getParameter(f) : "";
            
            f = "authorLastName";
            String bookAuthorLastName = request.getParameter(f) != null ? request.getParameter(f) : "";
            
            f = "publisherName";
            String bookPublisherName = request.getParameter(f) != null ? request.getParameter(f) : "";
            
            f = "publisherAddress";
            String bookPublisherAddress = request.getParameter(f) != null ? request.getParameter(f) : "";
            
            
            Book book = new Book();
            book.setTitle(bookTitle);            
            book.setDescription(bookDescription);
            book.setIsbn(bookIsbn);

            Author author = new Author();
            author.setFirstName(bookAuthorFirstName);            
            author.setLastName(bookAuthorLastName);
            book.setAuthor(author);

            Publisher publisher = new Publisher();
            publisher.setName(bookPublisherName);
            publisher.setAddress(bookPublisherAddress);
            book.setPublisher(publisher);
            
            int newBookID = bookRepo.addNewBook(book);
            
            if(newBookID != 0){
                result.put("status", "success");
                result.put("id", newBookID);
                result.put("message", "Sucessfully added new book.");
            } else {
                result.put("message", "Failed to added new book.");
            }
            
        }
        response.setContentType("application/json");
        out.println(result);
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doAddBook(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDisplayBook(request, response);
    }
}

