/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;
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


import com.soen387.session.com.soen387.session.core.HashCheck;

/**
 *
 * @author Louis-Simon
 */
public class Driver {
    
    
    public static void main(String[] args) {
        
        
       
        IBookRepository br = BookRepository.getInstance("Let me in!");
        
        //br.listAllBooks();
        
     
        
        //br.getBookInfo("2523665635437");
        //br.deleteBook(16);
        
        
        //br.deleteBook(20);
        boolean addBook = true;
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
       
    }
    
    
    
    
}
