/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.rules.ExpectedException;

/**
 *
 * @author Louis-Simon
 */
public class BookRepositoryTest {

    public BookRepositoryTest() {
    }

    protected String getTestImage() {
        return AppConfig.getInstance().getTestImagePath();
    }

    public static Session testSession;
    public static Book book;
    public static IBookRepository bookRepo;

    @BeforeClass
    public static void ClassSetUp() throws Exception {
        testSession = new Session();
        testSession.login("root", "root");
        bookRepo = BookRepository.getInstance("context");
    }

    /**
     * Test of addNewBook method, of class BookRepository.
     */
    //@Before
    public void setUp() throws Exception {
        book = new Book();
    }

    @Test
    public void testAddNewBook() throws RepositoryException, SQLException {
        System.out.println("addNewBook");

        Author auth = new Author();
        auth.setFirstName("AuthFname");
        auth.setLastName("AuthLname");

        Publisher pub = new Publisher();
        pub.setAddress("123 Test Street");
        pub.setName("Test Publishing");

        Book book = new Book("ID_TestBook", "ID > 0 testBook", "ISBN", auth, pub);

        int expResult = 0;
        int result = bookRepo.addNewBook(testSession, book);

        assertTrue(result > expResult);

    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testDuplicateISBN() {

        boolean thrown = true;
        System.out.println("duplicateISBN");

        Author auth = new Author();
        auth.setFirstName("AuthFname");
        auth.setLastName("AuthLname");

        Publisher pub = new Publisher();
        pub.setAddress("123 Test Street");
        pub.setName("Test Publishing");

        Book book = new Book("DuplicateISBN_TestBook", "Duplicate ISBN testBook", "ISBN", auth, pub);

        try {
            bookRepo.addNewBook(testSession, book);
        } catch (RepositoryException ex) {
            //Logger.getLogger(BookRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
            thrown = false;
        } catch (SQLException ex) {
            thrown = false;
            //Logger.getLogger(BookRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            assertTrue(thrown);
        }

    }

    @Test
    public void testSetBookCover() throws Exception {

        CoverImage cover = new CoverImage();
        InputStream is = new FileInputStream(new File(this.getTestImage()));
        cover.setContent(is);

        cover.setMime("image/jpeg");
        cover.setName("download.jpg");
        cover.setThumbContent(is);
        assertTrue(bookRepo.setCoverImage(testSession, 2, cover));

    }

    @Test
    public void testGetBookCover() throws Exception {

        CoverImage cover = new CoverImage();

        cover = bookRepo.getCoverImage(testSession, 1);

        assertNotNull(cover);
    }

    @Test
    public void testBookCover() throws Exception {
        System.out.println("bookCovers");

        //Set cover image and store it
        CoverImage coverA = new CoverImage();
        InputStream is = new FileInputStream(new File(this.getTestImage()));
        coverA.setContent(is);
        coverA.setMime("image/jpeg");
        coverA.setName("download.jpg");
        coverA.setThumbContent(is);
        bookRepo.setCoverImage(testSession, 3, coverA);

        //Get cover image and compare
        CoverImage coverB = new CoverImage();
        coverB = bookRepo.getCoverImage(testSession, 3);

        assertEquals(coverA, coverB);
    }

    @Test
    public void testDeleteNonExistingBook() {

        boolean thrown = true;
        try {
            bookRepo.deleteBook(testSession, 172);
        } catch (RepositoryException ex) {
            Logger.getLogger(BookRepositoryTest.class.getName()).log(Level.SEVERE, null, ex);
            thrown = false;
        } finally {
            assertTrue(thrown);
        }

    }

    // @Test
    public void testUpdatingBookInfo() {

    }

    @Test
    public void testListAllBooks() throws RepositoryException {
        ArrayList<Book> books = new ArrayList<>();
        books = bookRepo.listAllBooks(testSession);
        assertNotNull(books.size());
    }

    @Test
    public void testGetBookInfo_ID() throws RepositoryException {
        Book testBook = new Book();
        book = new Book();
        book = bookRepo.getBookInfo(testSession, 0);
        assertNotEquals(bookRepo, testBook);
    }

    @Test
    public void testGetBookInfo_ISBN() throws RepositoryException {
        Book testBook = new Book();
        book = new Book();
        book = bookRepo.getBookInfo(testSession, "3524142813508");
        assertNotEquals(bookRepo, testBook);
    }

    
}
