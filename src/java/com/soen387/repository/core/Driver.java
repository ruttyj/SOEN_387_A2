/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;

/**
 *
 * @author Louis-Simon
 */
public class Driver {
    
    
    public static void main(String[] args) {
        
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
    }
    
    
    
    
}
