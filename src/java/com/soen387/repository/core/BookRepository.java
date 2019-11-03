/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;


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
    PreparedStatement pstmt = null;
    ResultSet res = null;

    static final String USER = "root";
    static final String PASS = "root";

    //Singleton Pattern Implementation
    private static IBookRepository IRepository = null;

    private BookRepository() {
    }

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
        try {
            System.out.println("Listing all books...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            stmt = conn.createStatement();
            String sql;
            sql = "SELECT * FROM books";
            res = stmt.executeQuery(sql);

            
            while (res.next()) {
                Book result = this.makeBookFromRow(res);
                results.add(result);

                System.out.print("Book ID: " + result.getId());
                System.out.print(" Title: " + result.getTitle());
                System.out.print(" Description: " + result.getDescription());
                System.out.print(" ISBN: " + result.getIsbn());
                System.out.print(" Author: " + result.getAuthor().getFirstName() + " " + result.getAuthor().getLastName());
                System.out.print(" Publisher: " + result.getPublisher().getName());
                System.out.println(" Address: " + result.getPublisher().getAddress());
            }
        } catch (Exception exc) {
            exc.printStackTrace();
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
        return results;
    }

    @Override
    public Book getBookInfo(int id) {
        
        Book result = null;
        try {
            System.out.println("Listing book #" + id + "'s information...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            String sql;
            sql = "SELECT * FROM books WHERE id=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setInt(1, id);
            System.out.println("Created preparedStatement...");
            res = pstmt.executeQuery();

            while (res.next()) {
                result = this.makeBookFromRow(res);
                
                System.out.print("Book ID: " + result.getId());
                System.out.print(" Title: " + result.getTitle());
                System.out.print(" Description: " + result.getDescription());
                System.out.print(" ISBN: " + result.getIsbn());
                System.out.print(" Author: " + result.getAuthor().getFirstName() + " " + result.getAuthor().getLastName());
                System.out.print(" Publisher: " + result.getPublisher().getName());
                System.out.println(" Address: " + result.getPublisher().getAddress());
                
                // Only returning one result so stop...
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
        try {
            System.out.println("Listing book with ISBN: " + isbn + "'s information...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            String sql;
            sql = "SELECT * FROM books WHERE isbn=?";
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, isbn);
            System.out.println("Created preparedStatement...");
            res = pstmt.executeQuery();

            while (res.next()) {
                result = this.makeBookFromRow(res);
                
                System.out.print("Book ID: " + result.getId());
                System.out.print(" Title: " + result.getTitle());
                System.out.print(" Description: " + result.getDescription());
                System.out.print(" ISBN: " + result.getIsbn());
                System.out.print(" Author: " + result.getAuthor().getFirstName() + " " + result.getAuthor().getLastName());
                System.out.print(" Publisher: " + result.getPublisher().getName());
                System.out.println(" Address: " + result.getPublisher().getAddress());
                
                // Only returning one result so stop...
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
    public int addNewBook(Book book) {
        int id = 0;

        try {
            System.out.println("Adding new book to repository...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            String sql;
            sql = "INSERT INTO books (title, description, isbn, author_fname, author_lname, publisher_company, publisher_address) VALUES (?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, book.getTitle());
            pstmt.setString(2, book.getDescription());
            pstmt.setString(3, book.getIsbn());
            pstmt.setString(4, book.getAuthor().getFirstName());
            pstmt.setString(5, book.getAuthor().getLastName());
            pstmt.setString(6, book.getPublisher().getName());
            pstmt.setString(7, book.getPublisher().getAddress());
            pstmt.setInt(8, 0);
            System.out.println("Created preparedStatement...");
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating failed, no ID obtained.");
                }
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
        System.out.println("Book #" + id + " created...");
        return id;
    }

    
    @Override
    public void updateBookInfo(int id, Book book){
        try {
            System.out.println("Adding new book to repository...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

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
    }

    @Override
    public void setCoverImage() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteBook(int id) {
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
    }

    @Override
    public void deleteAllBooks() {
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
    }

    
    protected Book makeBookFromRow(ResultSet row){
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
