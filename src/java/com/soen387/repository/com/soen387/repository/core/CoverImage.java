/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.soen387.repository.com.soen387.repository.core;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


import java.io.ByteArrayOutputStream;
import java.io.InputStream;

/**
 *
 * @author Louis-Simon
 */
@Entity
public class CoverImage implements Serializable {
    
    private String mime;
    private InputStream blob;
   
    public void setBlob(InputStream blob){
        this.blob = blob;
    }
    
    public InputStream getBlob(){
        return this.blob;
    }
    
    
    public void setMime(String mime){
        this.mime = mime;
    }
    
    public String getMime(){
        return this.mime;
    }
    


//===========AUTO-GENERATED NETBEANS STUFF
    
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoverImage)) {
            return false;
        }
        CoverImage other = (CoverImage) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soen387.repository.core.CoverImage[ id=" + id + " ]";
    }
    
}
