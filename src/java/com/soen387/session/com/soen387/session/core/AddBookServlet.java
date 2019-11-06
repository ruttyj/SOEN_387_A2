package com.soen387.session.com.soen387.session.core;

import com.soen387.repository.com.soen387.repository.core.IBookRepository;
import com.soen387.repository.com.soen387.repository.core.BookRepository;
import com.soen387.repository.com.soen387.repository.core.Book;
import com.soen387.repository.com.soen387.repository.core.Author;
import com.soen387.repository.com.soen387.repository.core.Publisher;
import com.soen387.repository.com.soen387.repository.core.CoverImage;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.Part;
import java.io.InputStream;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.*;
import java.nio.file.Paths;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/addBook")
@MultipartConfig(maxFileSize=1024*1024*50)
public class AddBookServlet extends BaseProtectedPage {
    
    public void doDisplayBook(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession(true);
        if(this.checkLoggedIn(session, response)){
            PrintWriter out = response.getWriter();
            
            //Get the required view
            RequestDispatcher view = request.getRequestDispatcher("WEB-INF/templates/protectedPage.jsp"); 
            
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
        JSONObject errors = new JSONObject();
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
                } catch (Exception ex){
                    
                }
            }
            
            
            boolean valid = true;
            Book existingBook = bookRepo.getBookInfo(bookIsbn);
            if(existingBook != null){
                valid = false;
                JSONArray isbnErrors = new JSONArray();
                isbnErrors.add("ISBN must be unique.");
                errors.put("isbn", isbnErrors);
            }
               
            // If passes validation
            if(valid){
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

                    if(cover != null){
                        bookRepo.setCoverImage(newBookID, cover);
                    }
                    result.put("status", "success");
                    result.put("id", newBookID);
                    result.put("message", "Sucessfully added new book.");
                } else {
                    result.put("message", "Failed to added new book.");
                }
            } else {
                result.put("errors", errors);
                result.put("message", "Error invalid fields");
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

