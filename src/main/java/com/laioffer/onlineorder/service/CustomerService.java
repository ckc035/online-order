package com.laioffer.onlineorder.service;

import com.laioffer.onlineorder.dao.CustomerDao;
import com.laioffer.onlineorder.entity.Cart;
import com.laioffer.onlineorder.entity.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

//Service includes @component + bussiness service related
@Service
public class CustomerService {

    private CustomerDao customerDao;

    private PasswordEncoder passwordEncoder;

    @Autowired
    public CustomerService(CustomerDao customerDao, PasswordEncoder passwordEncoder) {
        this.customerDao = customerDao;
        this.passwordEncoder = passwordEncoder;
    }
    //frontend signupController sends customer json to here. We use methods in CustomerDao
    public void signUp(Customer customer) {
        Cart cart = new Cart();
        // customer will cascade, we need to make sure cart in customer.java is not null
        customer.setCart(cart);
        customer.setEnabled(true);
        customer.setPassword(passwordEncoder.encode(customer.getPassword())); // encode pw
        customerDao.signUp(customer);
    }

    public Customer getCustomer(String email) {
        return customerDao.getCustomer(email);
    }
}
