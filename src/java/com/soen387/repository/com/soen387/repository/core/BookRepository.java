/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;

import java.sql.*;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.io.*;






/**
 *
 * @author Louis-Simon
 * @contributor Jordan Rutty
 */
public class BookRepository implements IBookRepository {

    //JDBC Setup
    Connection conn = null;

    String baseSelect = "id, title, description, isbn, author_fname, author_lname, publisher_company, publisher_address, cover_image_mime IS NOT NULL as hasCover";

    //Singleton Pattern Implementation
    private static IBookRepository IRepository = null;

    private BookRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
    }

    
    protected String getDbHost(){
        return AppConfig.getInstance().getDbHost();
    }
    
    protected String getDbUser(){
        return AppConfig.getInstance().getDbUser();
    }
    
    protected String getDbPass(){
        return AppConfig.getInstance().getDbPassword();
    }
    
    public static IBookRepository getInstance(Object context) {
        if(context == null)
            return null;
        
        if (IRepository == null) {
            synchronized (BookRepository.class) {
                if (IRepository == null) {
                    IRepository = new BookRepository();
                }
            }
        }
        return IRepository;
    }
    
    /* DEPRECATED */
    public static IBookRepository getInstance(String context) {
        if(context == null)
            return null;
        
        if (IRepository == null) {
            synchronized (BookRepository.class) {
                if (IRepository == null) {
                    IRepository = new BookRepository();
                }
            }
        }
        return IRepository;
    }
    
    @Override
    public ArrayList<Book> listAllBooks(Session session) throws RepositoryException {
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        ArrayList<Book> results = new ArrayList<Book>();
        ResultSet res = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT "+this.baseSelect+" FROM books");
            while (res.next()) {
                results.add(this.makeBookFromRow(res));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return results;
    }

    @Override
    public Book getBookInfo(Session session, int id) throws RepositoryException {
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        Book result = null;
        ResultSet res = null;
        PreparedStatement pstmt = null;
        try {
            System.out.println("Listing book #" + id + "'s information...");
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());
            pstmt = conn.prepareStatement("SELECT "+this.baseSelect+" FROM books WHERE id=?");
            pstmt.setInt(1, id);
            res = pstmt.executeQuery();
            while (res.next()) {
                result = this.makeBookFromRow(res);
                break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }

    @Override
    public Book getBookInfo(Session session, String isbn) throws RepositoryException {
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        Book result = null;
        
        ResultSet res = null;
        PreparedStatement pstmt = null;
        
        try {
            System.out.println("Listing book with ISBN: " + isbn + "'s information...");
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());

            pstmt = conn.prepareStatement("SELECT "+this.baseSelect+" FROM books WHERE isbn=?");
            pstmt.setString(1, isbn);
            
            res = pstmt.executeQuery();

            while (res.next()) {
                result = this.makeBookFromRow(res);
                break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }

    private int getNextBookID(Connection conn) {
        int id = 1;
        ResultSet res = null;
        Statement stmt = null;
        
        try {
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT IFNULL(MAX(id)+1, 1) as max FROM books");
            
            while (res.next()) {
                id = res.getInt("max");
                break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return id;
    }

    @Override
    public int addNewBook(Session session, Book book) throws RepositoryException, SQLException {
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        int id = 1;
        PreparedStatement pstmt = null;
        
        try {
            System.out.println("Adding new book to repository...");
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());
            conn.setAutoCommit(false);
            
            String sql = "INSERT INTO books (title, description, isbn, author_fname, author_lname, publisher_company, publisher_address, id) VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getDescription());
            pstmt.setString(3, book.getIsbn());
            pstmt.setString(4, book.getAuthor().getFirstName());
            pstmt.setString(5, book.getAuthor().getLastName());
            pstmt.setString(6, book.getPublisher().getName());
            pstmt.setString(7, book.getPublisher().getAddress());
            
            id = this.getNextBookID(conn);
            System.out.println("Next ID: " + id);
            pstmt.setInt(8, id);
            pstmt.executeUpdate();
            
            conn.commit();

        } catch (SQLException ex) {
            //Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
            System.out.println(ex.getClass());
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException se) {
                
            }
        }
        //System.out.println("Book #" + id + " created...");
        return id;
    }
    
    @Override
    public void updateBookInfo(Session session, int id, Book book) throws RepositoryException{
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        PreparedStatement pstmt = null;
        try {
            System.out.println("Adding new book to repository...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());
            conn.setAutoCommit(false);
            System.out.println("Updating statement...");
            String sql = "UPDATE books SET title = ?, description = ?, isbn = ?, author_fname = ?, author_lname = ?, publisher_company = ?, publisher_address = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getDescription());
            pstmt.setString(3, book.getIsbn());
            pstmt.setString(4, book.getAuthor().getFirstName());
            pstmt.setString(5, book.getAuthor().getLastName());
            pstmt.setString(6, book.getPublisher().getName());
            pstmt.setString(7, book.getPublisher().getAddress());
            
            pstmt.setInt(8, id);
            
            int affectedRows = pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
    }
    
    @Override
    public boolean setCoverImage(Session session, int id, CoverImage cover) throws RepositoryException{
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        PreparedStatement pstmt = null;
        int affectedRows = 0;
        try {
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());
            conn.setAutoCommit(false);
            String sql;
            sql = "UPDATE books SET cover_image_name = ?, cover_image_mime = ?, cover_image_blob = ?, cover_image_thumb_blob = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            if(cover == null){
                pstmt.setString(1, null);
                pstmt.setString(2, null);
                pstmt.setNull(3, java.sql.Types.BLOB);
                pstmt.setNull(4, java.sql.Types.BLOB);
            } else {
                
                // Cone the input stream
                InputStream content = cover.getContent();
                InputStream imageContent = null;
                InputStream thumbContent = null;
                if(content != null){
                    try{
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    byte[] buffer = new byte[1024];
                    int len;
                    while ((len = content.read(buffer)) > -1 ) {
                        baos.write(buffer, 0, len);
                    }
                    baos.flush();

                    imageContent = new ByteArrayInputStream(baos.toByteArray()); 
                    thumbContent = ImageManip.resizeImage(new ByteArrayInputStream(baos.toByteArray()), cover.getName(), 200); 
                } catch(Exception ex){}

                }

                pstmt.setString(1, cover.getName());
                pstmt.setString(2, cover.getMime());
                pstmt.setBlob(3, imageContent);
                pstmt.setBlob(4, thumbContent);
            }
            
            pstmt.setInt(5, id);
            
            affectedRows = pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        
        return affectedRows != 0;
    }
    
    @Override
    public boolean clearCoverImage(Session session, int id) throws RepositoryException{
        return this.setCoverImage(session, id, null);
    }

    @Override
    public CoverImage getCoverImage(Session session, int id) throws RepositoryException{
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        CoverImage result = null;
        ResultSet res = null;
        PreparedStatement pstmt = null;
        
        try {
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());

            pstmt = conn.prepareStatement("SELECT cover_image_mime, cover_image_name, cover_image_blob, cover_image_thumb_blob FROM books WHERE id=?");
            pstmt.setInt(1, id);
            
            res = pstmt.executeQuery();

            while (res.next()) {
                result = this.makeCoverImageFromRow(res);
                break;
            }

        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (res != null) {
                    res.close();
                }
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
        return result;
    }
    
    @Override
    public void deleteBook(Session session, int id) throws RepositoryException {
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        PreparedStatement pstmt = null;
        try {
            System.out.println("Deleting book #" + id + "'s from the repository...");
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());

            String sql = "delete FROM books WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            pstmt.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }

    @Override
    public void deleteAllBooks(Session session) throws RepositoryException {
        if(session == null || !session.isUserLoggedIn())
            throw new RepositoryException();
        
        PreparedStatement pstmt = null;
        try {
            System.out.println("Deleting all books from the repository...");
            conn = DriverManager.getConnection(this.getDbHost(), this.getDbUser(), this.getDbPass());

            String sql = "DELETE FROM books";
            pstmt = conn.prepareStatement(sql);
            pstmt.executeUpdate();

            System.out.println("All books removed...");
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (pstmt != null) {
                    pstmt.close();
                }
                if (conn != null) {
                    conn.close();
                }

            } catch (SQLException se) {
                se.printStackTrace();
            }
        }
    }
    
    protected Book makeBookFromRow(ResultSet res){
        Book book = null;
        try {
            book = new Book();
            book.setID(res.getInt("id"));
            book.setTitle(res.getString("title"));            
            book.setDescription(res.getString("description"));
            book.setIsbn(res.getString("isbn"));
            book.setHasCover(res.getBoolean("hasCover"));

            Author author = new Author();
            author.setFirstName(res.getString("author_fname"));            
            author.setLastName(res.getString("author_lname"));
            book.setAuthor(author);
            
            Publisher publisher = new Publisher();
            publisher.setName(res.getString("publisher_company"));
            publisher.setAddress(res.getString("publisher_address"));
            book.setPublisher(publisher);
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return book;
    }
    
    protected CoverImage makeCoverImageFromRow(ResultSet res){
        CoverImage cover = null;
        try {
            byte[] contentBytes = res.getBytes("cover_image_blob");
            InputStream coverContent = contentBytes.length == 0 ? null : new ByteArrayInputStream(contentBytes);
            
            byte[] thumbContentBytes = res.getBytes("cover_image_thumb_blob");
            InputStream thumbContent = thumbContentBytes.length == 0 ? null : new ByteArrayInputStream(thumbContentBytes);
            
            cover = new CoverImage();
            cover.setMime(res.getString("cover_image_mime"));
            cover.setName(res.getString("cover_image_name"));
            cover.setThumbContent(thumbContent); 
            cover.setContent(coverContent);    
        } catch (SQLException ex) {
            System.out.println("FFFFF");
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
        } 
        return cover;
    }
}
