package com.soen387.session.core;

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

import java.util.Enumeration;
import java.io.*;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/viewBooks")
public class BookServlet extends BaseProtectedPage {
    
    protected JSONObject makeBookResource(Book book){
        JSONObject result = null;
        if(book != null){        
            result = new JSONObject();
            result.put("id", book.getID());
            result.put("title", book.getTitle());
            result.put("description", book.getDescription());
            result.put("isbn", book.getIsbn());

            Author author = book.getAuthor();
            if(author != null)
                result.put("author", makeAuthorResource(author));

            Publisher publisher = book.getPublisher();
            if(publisher != null)
                result.put("publisher", makePublisherResource(publisher));
        }
        return result;
    }
    
    protected JSONObject makeAuthorResource(Author author){
        JSONObject result = new JSONObject();
        result.put("firstName", author.getFirstName());        
        result.put("lastName", author.getLastName());
        return result;
    }
    
    protected JSONObject makePublisherResource(Publisher publisher){
        JSONObject result = new JSONObject();
        result.put("name", publisher.getName());        
        result.put("address", publisher.getAddress());
        return result;
    }
    
    
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(this.checkLoggedIn(session, response)){
            PrintWriter out = response.getWriter();
            
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/singlePage.jsp"); 
            
            // Get page data
            JSONObject initalData = new JSONObject();
            initalData.put("userData", getUserJSON(session));
            
            // Book list
            JSONObject pageData = new JSONObject();
            JSONArray bookListJson = new JSONArray();
            IBookRepository bookRepo = BookRepository.getInstance("context");
            ArrayList<Book> allBooks = bookRepo.listAllBooks();
            int i;
            Book currentBook;
            for(i = 0; i < allBooks.size(); ++i){
                currentBook = allBooks.get(i);
                if(currentBook != null)
                    bookListJson.add(this.makeBookResource(currentBook));
            }
            pageData.put("books", bookListJson);
            
            initalData.put("pageData", pageData);
            initalData.put("pageDataCount", bookRepo.listAllBooks().size());
            
            
            // Inject data into view
            request.setAttribute("initalData", initalData.toString());
            request.setAttribute("script", "viewBooks.js");
            
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

