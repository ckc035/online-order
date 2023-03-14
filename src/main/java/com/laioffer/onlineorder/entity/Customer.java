package com.laioffer.onlineorder.entity;

import javax.persistence.*;
import java.io.Serializable;

// jackson
// for the ease of importing the string input json from user
// and turn it to sth we use in the backend, no need to turn it
// to json one line by one line
// then put it back as json and send it back to user
@Entity
@Table (name = "customers")
public class Customer implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private boolean enabled;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(unique = true)
    // default column name here will be cart_id (id is the pk in entity Cart)
    private Cart cart;
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return  enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

}

