/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;
import java.io.*;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author ruttyj
 */
public class AppConfig {
    
    // Comment switch chage the first line /*/ to //*/  or vice versa to change
    /*/ 
    private String userJsonFilePath = "C:\\Programming\\SOEN_387_A2\\users.JSON";
    private String noImagePath = "C:\\Programming\\SOEN_387_A2\\noImage.png";
    private String testImagePath = "C:\\Programming\\SOEN_387_A2\\downloadj.jpg";
    /*/
    private String userJsonFilePath = "C:\\Users\\Louis-Simon\\Documents\\NetBeansProjects\\SOEN_387_A2\\users.JSON";
    private String noImagePath = "C:\\Users\\Louis-Simon\\Documents\\NetBeansProjects\\SOEN_387_A2\\noImage.png";
    private String testImagePath = "C:\\Users\\Louis-Simon\\Documents\\NetBeansProjects\\SOEN_387_A2\\downloadj.jpg";
    //*/
    
    
    
    private String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private String DB_URL = "jdbc:mysql://localhost:3306/demo?autoReconnect=true&useSSL=false";
    private String PASS = "root";

    Properties config = null;
    
    private static AppConfig instance = null;

    private AppConfig(){
        this.config = new Properties();
        try {
            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
            InputStream input = classLoader.getResourceAsStream("com/soen387/repository/com/soen387/repository/core/config.properties");
            
            this.config = new Properties();
            this.config.load(input);
        } catch(Exception ex){
            Logger.getLogger(AppConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static AppConfig getInstance(){
        if(instance == null){
            synchronized (AppConfig.class) {
                instance = new AppConfig();
            }
        }
        return instance;
    }
    
    public String getUserJsonFilePath(){
        return this.config.getProperty("userJsonFilePath");
    }
    
    public String getDbDriver(){
        return this.config.getProperty("jbdcDriver");
    }
    
    public String getDbHost(){
        return this.config.getProperty("dbHost");
    }
    
    public String getDbUser(){
        return this.config.getProperty("dbUser");
    }
    public String getDbPassword(){
        return this.config.getProperty("dbPass");
    }
    
    public String getNoImagePath(){
        return this.config.getProperty("noImagePath");
    }
    
    public String getTestImagePath() {
        return this.config.getProperty("testImagePath");
    }
}
