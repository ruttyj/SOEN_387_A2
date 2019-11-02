/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.core;

import java.io.File;
/**
 *
 * @author ruttyj
 */
public class AppConfig {
    
    // Comment switch chage the first line /*/ to //*/  or vice versa to change
    //*/ 
    private String userJsonFilePath = "C:\\Programming\\SOEN_387_A2\\users.JSON";
    /*/
    private String userJsonFilePath = "C:\\Users\\Louis-Simon\\Documents\\NetBeansProjects\\SOEN_387_A2\\users.JSON";
    //*/

    
    private static AppConfig instance = null;

    private AppConfig(){}
    
    public static AppConfig getInstance(){
        if(instance == null){
            synchronized (AppConfig.class) {
                instance = new AppConfig();
            }
        }
        return instance;
    }
    
    
    public String getUserJsonFilePath(){
        return this.userJsonFilePath;
    }
    
}
