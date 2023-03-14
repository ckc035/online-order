package com.laioffer.onlineorder.controller;

import com.laioffer.onlineorder.entity.Customer;
import com.laioffer.onlineorder.service.CustomerService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.laioffer.onlineorder.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;

// use @Controller to mark this class as a web component of spring mvc, so it will
// register the methods annotated with @RequestMapping (used to define REST API)
// http request -> dispatcherServlet -> controller
@Controller
public class SignUpController {

    private CustomerService customerService;

    // dependency injection
    @Autowired
    public SignUpController(CustomerService customerService) {
        this.customerService = customerService;
    }



    //client url with /signup (value) --> we use method POST for sign up
    @RequestMapping(value = "/signup", method = RequestMethod.POST)
    // we assume it will always be created (may not be good, as same email should not
    // be created more than once. we should write response in the method ourself)
    @ResponseStatus(value = HttpStatus.CREATED)
    // @RequestBody converts http request body (JSON) into Customer/backend object, which includes info the caller provided
    public void signUp(@RequestBody Customer customer) {
        customerService.signUp(customer);
    }
}



