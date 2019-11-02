package com.soen387.session.core;


import com.soen387.repository.core.AppConfig;
import com.soen387.repository.core.User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author Louis-Simon
 */
public class HashCheck {
    private String username;
    private String password;

    public static JSONParser parser = new JSONParser();
    public static Object obj;
    public static JSONObject userObject;
    public static String jsonUsername;
    public static String jsonPassword;



    // User object from request fields
    public HashCheck(String user, String pw) {
        this.username = user;
        this.password = pw;
    }

    // Receive user password from login, compute its MD5 hash and return it
    public static String computeMD5(String pw) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(pw.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter.printHexBinary(digest);
        return myHash;
    }

    private static File getUserFile(){
        return new File(AppConfig.getInstance().getUserJsonFilePath());
    }
    
    
    public static User getMatchingUser(String username, String password){
        User result = null;
        try {
            String pwHash = HashCheck.computeMD5(password);

            File file = getUserFile();
            
            try {
                obj = parser.parse(new FileReader(file));
                JSONObject users = (JSONObject) obj;
                JSONArray usersArray = (JSONArray) users.get("users");
                Iterator itr = usersArray.iterator();

                int jsonId;
                String jsonUsername;
                String jsonPassword;
                while (itr.hasNext()) {
                    userObject = (JSONObject) itr.next();
                    jsonId = (int)(long)userObject.get("id");
                    jsonUsername = (String) userObject.get("username");
                    jsonPassword = (String) userObject.get("password");
                    if ((jsonPassword.equalsIgnoreCase(pwHash)) && (jsonUsername.equals(username))) {
                        result = new User();
                        result.setId(jsonId);
                        result.setUsername(jsonUsername);
                        break;
                    }
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(HashCheck.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(HashCheck.class.getName()).log(Level.SEVERE, null, ex);
            } catch (ParseException ex) {
                Logger.getLogger(HashCheck.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch ( NoSuchAlgorithmException ex){
            Logger.getLogger(HashCheck.class.getName()).log(Level.SEVERE, null, ex);
        }

        return result;
    }
    
    
    
    // Receive username + MD5 hashed password and compare the hash with the stored password
    public static boolean compareHashes(String user, String pwHash) {
        File file = getUserFile();
        try {
            obj = parser.parse(new FileReader(file));
            JSONObject users = (JSONObject) obj;
            JSONArray usersArray = (JSONArray) users.get("users");
            Iterator itr = usersArray.iterator();

            String jsonUsername;
            String jsonPassword;
            while (itr.hasNext()) {
                userObject = (JSONObject) itr.next();
                jsonUsername = (String) userObject.get("username");
                jsonPassword = (String) userObject.get("password");
                if ((jsonPassword.equalsIgnoreCase(pwHash)) && (jsonUsername.equals(user))) {
                    return true;
                }
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(HashCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(HashCheck.class.getName()).log(Level.SEVERE, null, ex);
        } catch (ParseException ex) {
            Logger.getLogger(HashCheck.class.getName()).log(Level.SEVERE, null, ex);
        }

        return false;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
