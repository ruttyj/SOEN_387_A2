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

/**
 *
 * @author Louis-Simon
 * @contributor Jordan Rutty
 */
public class BookRepository implements IBookRepository {

    //JDBC Setup
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/demo";

    Connection conn = null;
    Statement stmt = null;

    static final String USER = "root";
    static final String PASS = "root";

    //Singleton Pattern Implementation
    private static IBookRepository IRepository = null;

    private BookRepository() {
        try {
            Class.forName("com.mysql.jdbc.Driver").newInstance();
        } catch (Exception ex) {
        }
    }

    // SecurityContext can be used to limit the users interaction with the repository based on permissions (which dont exis't in this project)
    public static IBookRepository getInstance(SecurityContext securityContext) {
        if(securityContext == null)
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
    public ArrayList<Book> listAllBooks() {
        ArrayList<Book> results = new ArrayList<Book>();
        ResultSet res = null;
        Statement stmt = null;
        try {
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            stmt = conn.createStatement();
            res = stmt.executeQuery("SELECT * FROM books");
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
    public Book getBookInfo(int id) {
        Book result = null;
        ResultSet res = null;
        PreparedStatement pstmt = null;
        try {
            System.out.println("Listing book #" + id + "'s information...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            pstmt = conn.prepareStatement("SELECT * FROM books WHERE id=?");
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
        return result;
    }

    @Override
    public Book getBookInfo(String isbn) {
        Book result = null;
        
        ResultSet res = null;
        PreparedStatement pstmt = null;
        
        try {
            System.out.println("Listing book with ISBN: " + isbn + "'s information...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            pstmt = conn.prepareStatement("SELECT * FROM books WHERE isbn=?");
            pstmt.setString(1, isbn);
            
            
            System.out.println("Created preparedStatement...");
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
        return result;
    }

    
    
    
    protected int getNextBookID(Connection conn) {
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
    public int addNewBook(Book book) {
        int id = 1;
        PreparedStatement pstmt;
        
        try {
            System.out.println("Adding new book to repository...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            
            String sql;
            sql = "INSERT INTO books (title, description, isbn, author_fname, author_lname, publisher_company, publisher_address, id, cover_image_mime, cover_image_blob) VALUES (?,?,?,?,?,?,?,?, NULL, NULL)";
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
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
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
        System.out.println("Book #" + id + " created...");
        return id;
    }
    
    @Override
    public void updateBookInfo(int id, Book book){
        PreparedStatement pstmt = null;
        try {
            System.out.println("Adding new book to repository...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);
            conn.setAutoCommit(false);
            System.out.println("Updating statement...");
            String sql;
            sql = "UPDATE books SET title = ?, description = ?, isbn = ?, author_fname = ?, author_lname = ?, publisher_company = ?, publisher_address = ? WHERE id = ?";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getDescription());
            pstmt.setString(3, book.getIsbn());
            pstmt.setString(4, book.getAuthor().getFirstName());
            pstmt.setString(5, book.getAuthor().getLastName());
            pstmt.setString(6, book.getPublisher().getName());
            pstmt.setString(7, book.getPublisher().getAddress());
            
            pstmt.setInt(8, id);
            
            System.out.println("Created preparedStatement...");
            int affectedRows = pstmt.executeUpdate();
            conn.commit();
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    @Override
    public void setCoverImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBook(int id) {
        PreparedStatement pstmt = null;
        try {
            System.out.println("Deleting book #" + id + "'s from the repository...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            String sql;
            sql = "delete FROM books WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            System.out.println("Created preparedStatement...");
            pstmt.executeUpdate();

            System.out.println("Book removed...");
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    @Override
    public void deleteAllBooks() {
        PreparedStatement pstmt = null;
        try {
            System.out.println("Deleting all books from the repository...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            String sql;
            sql = "DELETE FROM books";
            pstmt = conn.prepareStatement(sql);
            System.out.println("Created preparedStatement...");
            pstmt.executeUpdate();

            System.out.println("All books removed...");
        } catch (SQLException ex) {
            Logger.getLogger(BookRepository.class.getName()).log(Level.SEVERE, null, ex);
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
    }

    
    protected Book makeBookFromRow(ResultSet res){
        Book book = null;
        try {
            book = new Book();
            book.setID(res.getInt("id"));
            book.setTitle(res.getString("title"));            
            book.setDescription(res.getString("description"));
            book.setIsbn(res.getString("isbn"));

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
}
