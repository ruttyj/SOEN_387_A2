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
public interface IBookRepository {

    public void listAllBooks();

    public void getBookInfo(int id);

    public void getBookInfo(String isbn);

    public int addNewBook(String title, String description, String isbn, String authorFName, String authorLName, String publisher, String publisherAddress, int coverImage);

    public void updateBookInfo(int id, String title, String description); //Perhaps add more fields, not sure what "details" actual entails

    public void setCoverImage();

    public void deleteBook(int id);

    public void deleteAllBooks();
    
}
