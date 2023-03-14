package com.laioffer.onlineorder.entity;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

// Hibernate ORM this entity class into a table in DB
// authorities for user authorities.
// @Entity JPA annotations for ORM so that it can be mapped to DB
// Serialization = process of converting an object into a stream of bytes that can be transmitted over
// a network or saved to a file.
// Serializable == the instance of this class can be serializaed and deserialized (a stream of byte -> object)
// This allows persistence of data in DB and transfer of data between diff layers of applications
@Entity
@Table(name = "authorities")
public class Authorities implements Serializable {
    // first version, if change later, need to change it so that serialized and deserialized the same version
    private static final long serialVersionUID = 1L;

    // Using email as PK of this table
    // Id cannot be an object
    @Id
    private String email;

    private String authorities;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAuthorities() {
        return authorities;
    }

    public void setAuthorities(String authorities) {
        this.authorities = authorities;
    }
}
