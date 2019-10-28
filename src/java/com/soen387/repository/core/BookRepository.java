/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Louis-Simon
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
    private static BookRepository IRepository = new BookRepository();

    private BookRepository() {
    }

    public static BookRepository getIRepository() {
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
    public void listAllBooks() {

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

                int id = res.getInt("id");
                String title = res.getString("title");
                String description = res.getString("description");
                String isbn = res.getString("isbn");
                String author_fname = res.getString("author_fname");
                String author_lname = res.getString("author_fname");
                String publisher = res.getString("publisher_company");
                String publisher_address = res.getString("publisher_address");

                System.out.print("Book ID: " + id);
                System.out.print(" Title: " + title);
                System.out.print(" Description: " + description);
                System.out.print(" ISBN: " + isbn);
                System.out.print(" Author: " + author_fname + " " + author_lname);
                System.out.print(" Publisher: " + publisher);
                System.out.println(" Address: " + publisher_address);

                //System.out.println(res.getString(2) + ", " + res.getString(5) + ", " + res.getString(6));
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
    }

    @Override
    public void getBookInfo(int id) {
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

                String title = res.getString("title");
                String description = res.getString("description");
                String isbn = res.getString("isbn");
                String author_fname = res.getString("author_fname");
                String author_lname = res.getString("author_fname");
                String publisher = res.getString("publisher_company");
                String publisher_address = res.getString("publisher_address");

                System.out.print("Book ID: " + id);
                System.out.print(" Title: " + title);
                System.out.print(" Description: " + description);
                System.out.print(" ISBN: " + isbn);
                System.out.print(" Author: " + author_fname + " " + author_lname);
                System.out.print(" Publisher: " + publisher);
                System.out.println(" Address: " + publisher_address);
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
    }

    @Override
    public void getBookInfo(String isbn) {
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
                int id = res.getInt("id");
                String title = res.getString("title");
                String description = res.getString("description");

                String author_fname = res.getString("author_fname");
                String author_lname = res.getString("author_fname");
                String publisher = res.getString("publisher_company");
                String publisher_address = res.getString("publisher_address");

                System.out.print("Book ID: " + id);
                System.out.print(" Title: " + title);
                System.out.print(" Description: " + description);
                System.out.print(" ISBN: " + isbn);
                System.out.print(" Author: " + author_fname + " " + author_lname);
                System.out.print(" Publisher: " + publisher);
                System.out.println(" Address: " + publisher_address);
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
    }

    @Override
    public int addNewBook(String title, String description, String isbn, String authorFName, String authorLName, String publisher, String publisherAddress, int coverImage) {
        int id = 0;

        try {
            System.out.println("Adding new book to repository...");
            System.out.println("Connecting to database...");
            conn = DriverManager.getConnection(DB_URL, USER, PASS);

            System.out.println("Creating statement...");
            String sql;
            sql = "INSERT INTO books (title, description, isbn, author_fname, author_lname, publisher_company, publisher_address, cover_image) VALUES (?,?,?,?,?,?,?,?)";
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            pstmt.setString(1, title);
            pstmt.setString(2, description);
            pstmt.setString(3, isbn);
            pstmt.setString(4, authorFName);
            pstmt.setString(5, authorLName);
            pstmt.setString(6, publisher);
            pstmt.setString(7, publisherAddress);
            pstmt.setInt(8, 0);
            System.out.println("Created preparedStatement...");
            int affectedRows = pstmt.executeUpdate();

            if (affectedRows == 0) {
                throw new SQLException("Creating user failed, no rows affected.");
            }

            try (ResultSet generatedKeys = pstmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    id = generatedKeys.getInt(1);
                } else {
                    throw new SQLException("Creating user failed, no ID obtained.");
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
    public void updateBookInfo(int id, String title, String description) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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

}
