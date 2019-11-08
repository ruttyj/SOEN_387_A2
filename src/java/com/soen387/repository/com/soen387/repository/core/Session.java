/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;

/**
 *  Session Bean
 * @author Jordan Rutty
 */
public class Session {
    
    private User currentUser = null;
    
    public Session(){}
    
    public User getCurrentUser(){
        return this.currentUser;
    }
    
    public boolean isUserLoggedIn(){
        return currentUser != null;
    }
    
    public boolean login(String username, String password){
        User user = HashCheck.getMatchingUser(username, password);
        if (user != null) {
            this.currentUser = new User();
            this.currentUser.setId(user.getId());
            this.currentUser.setUsername(user.getUsername());
            return true;
        }
        return false;
    }
    
    public void logout(){
        this.currentUser = null;
    }
}
