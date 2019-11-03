/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;
import org.json.simple.JSONObject;

/**
 * Description: Helper class to convert object to front-end resources
 * @author ruttyj
 */
public class JsonResourceFactory {
    public static JSONObject makeBookResource(Book book){
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
    
    public static  JSONObject makeAuthorResource(Author author){
        JSONObject result = new JSONObject();
        result.put("firstName", author.getFirstName());        
        result.put("lastName", author.getLastName());
        return result;
    }
    
    public static  JSONObject makePublisherResource(Publisher publisher){
        JSONObject result = new JSONObject();
        result.put("name", publisher.getName());        
        result.put("address", publisher.getAddress());
        return result;
    }
}
