/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;

/**
 *
 * @author Louis-Simon
 */
class RepositoryException extends Exception 
{ 
    public RepositoryException() 
    { 
        super(""); 
    } 
    
    public RepositoryException(String s) 
    { 
        super(s); 
    } 
} 