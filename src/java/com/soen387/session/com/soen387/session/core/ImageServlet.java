package com.soen387.session.com.soen387.session.core;

import com.soen387.repository.com.soen387.repository.core.AppConfig;
import com.soen387.repository.com.soen387.repository.core.IBookRepository;
import com.soen387.repository.com.soen387.repository.core.BookRepository;
import com.soen387.repository.com.soen387.repository.core.CoverImage;
import com.soen387.repository.com.soen387.repository.core.Session;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import javax.activation.MimetypesFileTypeMap;
import java.io.*;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Jordan Rutty
 */
@WebServlet("/cover")
public class ImageServlet extends BaseProtectedPage {
    
    public void doRequest(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        businessSession = new Session(request);
        OutputStream outputStream = response.getOutputStream();

        boolean isImageDisplayed = false;
        if(this.isLoggedIn(request)){
            if(request.getParameter("id") != null){
                int bookID = Integer.parseInt(request.getParameter("id"));
                IBookRepository bookRepo = BookRepository.getInstance(this.getSecurityContext(request));
                CoverImage cover = null;
                try {
                    cover = bookRepo.getCoverImage(businessSession, bookID);
                } catch( Exception ex){
                    Logger.getLogger(AddBookServlet.class.getName()).log(Level.SEVERE, null, ex);
                } 
                
                // Display Page
                if(cover != null){
                    response.setContentType(cover.getMime());
                    this.copyDataToOutputSteam(cover.getContent(), outputStream);
                    isImageDisplayed = true;
                }
            }
        } 
        
        if(!isImageDisplayed){
            File noImageFile = new File(AppConfig.getInstance().getNoImagePath());
            InputStream inputStream = new FileInputStream(noImageFile);
            response.setContentType(new MimetypesFileTypeMap().getContentType(noImageFile));
            this.copyDataToOutputSteam(inputStream, outputStream);
        }
    }
    
    protected void copyDataToOutputSteam(InputStream inputStream, OutputStream outputStream) throws IOException {
        int bytesRead;
        byte[] buffer = new byte[1024];
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doRequest(request, response);
    }
}

