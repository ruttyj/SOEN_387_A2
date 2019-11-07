package com.soen387.session.com.soen387.session.core;

import com.soen387.repository.com.soen387.repository.core.IBookRepository;
import com.soen387.repository.com.soen387.repository.core.BookRepository;
import com.soen387.repository.com.soen387.repository.core.Book;
import com.soen387.repository.com.soen387.repository.core.Author;
import com.soen387.repository.com.soen387.repository.core.JsonResourceFactory;
import com.soen387.repository.com.soen387.repository.core.Publisher;
import com.soen387.repository.com.soen387.repository.core.CoverImage;

import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.nio.file.Paths;
import java.io.*;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/editBook")
@MultipartConfig(maxFileSize=1024*1024*50)
public class EditBookServlet extends BaseProtectedPage {
    
    public void doDisplayBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if(this.checkLoggedInPage(request, response)){
            
            PrintWriter out = response.getWriter();
            
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/protectedPage.jsp"); 
            
            // Get page data
            JSONObject initalData = new JSONObject();
            initalData.put("userData", getUserJSON());
            
            
            JSONObject pageData = new JSONObject();
            if(request.getParameter("id") != null){
                Book book = null;
                try {
                    int bookID = Integer.parseInt(request.getParameter("id"));
                    IBookRepository bookRepo = BookRepository.getInstance(this.getSecurityContext(request));
                    book = bookRepo.getBookInfo(businessSession, bookID);

                    // Display Page
                    if(book != null){
                        pageData.put("book", JsonResourceFactory.makeBookResource(book));
                    } else {
                        pageData.put("message", "No Book Found");
                    }
                } catch( Exception ex){
                    Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, ex);
                } 
            } else {
                pageData.put("message", "No Book Specified");
            }
            initalData.put("pageData", pageData);

            // Inject data into view
            request.setAttribute("initalData", initalData.toString());
            request.setAttribute("script", "editBook.js");

            // Output contents
            response.setContentType("text/html");
            view.include(request, response);
        } 
    }
    
    
    public void doEditBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        PrintWriter out = response.getWriter();
        JSONObject result = new JSONObject();
        JSONObject errors = new JSONObject();
        result.put("status", "failure");
        result.put("message", "");
        String message = "";
           
        if(this.checkLoggedInResponse(request, response)){
            // Collect Ids into ArrayList
           
            IBookRepository bookRepo = BookRepository.getInstance(this.getSecurityContext(request));

            String f;
            
            f = "id";
            int id = Integer.parseInt(request.getParameter(f) != null ? request.getParameter(f) : "");
            
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
            
            f = "clearCover";
            boolean clearCover = Boolean.parseBoolean(request.getParameter(f) != null ? request.getParameter(f) : "");
            
            
            boolean valid = true;
            try {
                Book existingIsbnBook = bookRepo.getBookInfo(businessSession, bookIsbn);
                if(existingIsbnBook != null){
                    if( existingIsbnBook.getID() != id){
                        valid = false;
                        JSONArray isbnErrors = new JSONArray();
                        isbnErrors.add("ISBN must be unique.");
                        errors.put("isbn", isbnErrors);
                        message = "Error invalid fields.";
                    }
                }

                Book book = bookRepo.getBookInfo(businessSession, id);
                if(book == null){
                    valid = false;
                    message = "Error book does not exist.";
                }


                // If passes validation
                if(valid){
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

                    bookRepo.updateBookInfo(businessSession, id, book);

                    if(clearCover){
                        bookRepo.clearCoverImage(businessSession, id);
                    } 


                    Part filePart = request.getPart("cover");
                    CoverImage cover = null;
                    if (filePart != null) {
                        try {
                            String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString();
                            String fileMime = filePart.getContentType();
                            InputStream fileContents = filePart.getInputStream();

                            cover = new CoverImage();
                            cover.setMime(fileMime);
                            cover.setContent(fileContents);
                            cover.setName(fileName);
                        } catch(Exception ex){
                            //was not a valid file
                        }
                    }
                    if(cover != null){
                        bookRepo.setCoverImage(businessSession, id, cover);
                    }
                }
                result.put("status", "success");
                result.put("message", "Sucessfully updated new book.");
            } catch( Exception ex){
                Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, ex);
            } 
            
            if(!valid) {
                result.put("errors", errors);
                result.put("message", message);
            }
        }
        response.setContentType("application/json");
        out.println(result);
    }
    
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doEditBook(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doDisplayBook(request, response);
    }
}

