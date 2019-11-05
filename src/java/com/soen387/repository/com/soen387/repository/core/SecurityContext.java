/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;

/**
 *
 * @author Jordan Rutty
 */
public class SecurityContext {
    private int userId;
    
    public int getUserId(){
        return userId;
    }
    
    public void setUserId(int id){
        this.userId = id;
    }
    
    public SecurityContext(){
        
    }
}
