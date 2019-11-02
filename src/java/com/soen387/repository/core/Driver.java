/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;
import static com.soen387.session.core.HashCheck.jsonPassword;
import static com.soen387.session.core.HashCheck.jsonUsername;
import static com.soen387.session.core.HashCheck.obj;
import static com.soen387.session.core.HashCheck.parser;
import static com.soen387.session.core.HashCheck.userObject;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import static java.lang.System.out;


import com.soen387.session.core.HashCheck;

/**
 *
 * @author Louis-Simon
 */
public class Driver {
    
    
    public static void main(String[] args) {
        
        try {
            obj = parser.parse(new FileReader(AppConfig.getInstance().getUserJsonFilePath()));
            JSONObject users = (JSONObject) obj;
            JSONArray usersArray = (JSONArray) users.get("users");
            Iterator itr = usersArray.iterator();

            while (itr.hasNext()) {
                userObject = (JSONObject) itr.next();
                jsonUsername = (String) userObject.get("username");
                jsonPassword = (String) userObject.get("password");
                out.println("jsonUsername:" + jsonUsername);
                out.println("jsonPassword:" + jsonPassword);
            }
        } catch (FileNotFoundException ex) {
            out.println("FileNotFoundException");
        } catch (IOException ex) {
            out.println("IOException");
        } catch (ParseException ex) {}

        
        
        /*
        BookRepository br = BookRepository.getInstance("Let me in!");
        
        //br.listAllBooks();
        
        int updateBookId = 2;
        Book fetchedBook = br.getBookInfo(updateBookId);
        fetchedBook.setTitle("BOB");
        br.updateBookInfo(updateBookId, fetchedBook);
        
        //br.getBookInfo("2523665635437");
        //br.deleteBook(16);
        
        
        
        
        
        br.deleteBook(20);
        boolean addBook = false;
        if(addBook){        
            Book book = new Book();
            book.setTitle("NEW BOOK");            
            book.setDescription("NEW DESCRIPTION");
            book.setIsbn("1234567891234");

            Author author = new Author();
            author.setFirstName("NEW");            
            author.setLastName("NEWNEW");
            book.setAuthor(author);

            Publisher publisher = new Publisher();
            publisher.setName("NEW PUBLISHING");
            publisher.setAddress("\"NEW PUBLISHING ADDRESS\"");
            book.setPublisher(publisher);
            int a = br.addNewBook(book);
            br.getBookInfo(a);
        }
        
        //br.deleteAllBooks();
        */
    }
    
    
    
    
}
