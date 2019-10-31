/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;

import java.sql.*;

/**
 *
 * @author Louis-Simon
 */
public class JDBCTest {

    //ID,Title,Descritpion,ISBN,Author,Publisher,Cover
    public static void main(String[] args) throws SQLException {

        Connection myConn = null;
        Statement myStmt = null;
        ResultSet myRes = null;

        String user = "root";
        String pw = "root";

        try {
            myConn = DriverManager.getConnection("jdbc:mysql://localhost:3306/demo", user, pw);
            myStmt = myConn.createStatement();
            myRes = myStmt.executeQuery("select * from books");

            while (myRes.next()) {

                System.out.println(myRes.getString(2) + ", " + myRes.getString(5) + ", " + myRes.getString(6));
            }
        } catch (Exception exc) {
            exc.printStackTrace();
        } finally {
            if (myRes != null) {
                myRes.close();
            }
            if (myStmt != null) {
                myStmt.close();
            }
            if (myConn != null) {
                myConn.close();
            }

        }

    }

}
