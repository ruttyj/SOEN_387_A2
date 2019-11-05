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

/**
 *
 * @author Louis-Simon
 */
@Entity
public class Author implements Serializable {

    private String author_fname;
    private String author_lname;

    public Author() {
    }


    public String getFirstName() {
        return author_fname;
    }

    public void setFirstName(String author_fname) {
        this.author_fname = author_fname;
    }

    public String getLastName() {
        return author_lname;
    }

    public void setLastName(String author_lname) {
        this.author_lname = author_lname;
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
        if (!(object instanceof Author)) {
            return false;
        }
        Author other = (Author) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.soen387.repository.core.Author[ id=" + id + " ]";
    }
    
}
