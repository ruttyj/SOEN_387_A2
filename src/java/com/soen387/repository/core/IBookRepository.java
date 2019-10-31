/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;
import java.util.ArrayList;

/**
 *
 * @author Louis-Simon
 */
public interface IBookRepository {

    public ArrayList<Book> listAllBooks();

    public Book getBookInfo(int id);

    public Book getBookInfo(String isbn);

    public int addNewBook(Book book);

    public void updateBookInfo(int id, Book book); 
    
    public void setCoverImage();

    public void deleteBook(int id);

    public void deleteAllBooks();
    
}
