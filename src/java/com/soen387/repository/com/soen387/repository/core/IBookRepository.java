/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;
import java.util.ArrayList;

/**
 *
 * @author Louis-Simon
 */
public interface IBookRepository {

    public ArrayList<Book> listAllBooks(Session session) throws RepositoryException;

    public Book getBookInfo(Session session, int id) throws RepositoryException;

    public Book getBookInfo(Session session, String isbn) throws RepositoryException;

    public int addNewBook(Session session, Book book) throws RepositoryException;

    public void updateBookInfo(Session session, int id, Book book) throws RepositoryException; 
    
    public boolean setCoverImage(Session session, int id, CoverImage cover) throws RepositoryException;
    
    public boolean clearCoverImage(Session session, int id) throws RepositoryException;
    
    public CoverImage getCoverImage(Session session, int id) throws RepositoryException;

    public void deleteBook(Session session, int id) throws RepositoryException;

    public void deleteAllBooks(Session session) throws RepositoryException;
    
}
