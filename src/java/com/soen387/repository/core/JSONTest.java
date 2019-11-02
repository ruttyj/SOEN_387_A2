/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;

import java.io.FileReader;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.Map;
import javax.xml.bind.DatatypeConverter;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author Louis-Simon
 */
public class JSONTest {

    private static final String testPW1 = "admin";
    private static final String testPW2 = "root";
    private static final String testPW3 = "soen387";

    public static void main(String[] args) throws NoSuchAlgorithmException {

        //MD5 Hash
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(testPW1.getBytes());
        byte[] digest = md.digest();
        String myHash = DatatypeConverter
                .printHexBinary(digest);
        //System.out.println(myHash);
        byte[] messageDigest;

        //JSON Stuff
        JSONParser parser = new JSONParser();
        Object obj;
        JSONObject usr;
        String jsonUser;
        String jsonPW;

        //Get each user, print username and password, digest password to confirm it matches with userPW input
        try {

            obj = parser.parse(new FileReader(AppConfig.getInstance().getUserJsonFilePath()));

            JSONObject userObject = (JSONObject) obj;

            JSONArray usersArray = (JSONArray) userObject.get("users");

            Iterator itr = usersArray.iterator();

            while (itr.hasNext()) {
                usr = (JSONObject) itr.next();
                jsonUser = (String) usr.get("username");
                jsonPW = (String) usr.get("password");
                if (jsonUser.equals(testPW1)) {
                    System.out.println(jsonPW.equalsIgnoreCase(myHash));
                }
                System.out.print("Username: " + jsonUser);
                System.out.println(" Password: " + jsonPW);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
