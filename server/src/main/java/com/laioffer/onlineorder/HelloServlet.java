package com.laioffer.onlineorder;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import org.json.JSONObject;
import org.apache.commons.io.IOUtils;
import com.laioffer.onlineorder.entity.Customer;
import com.fasterxml.jackson.databind.ObjectMapper;

// 只要url发送了/hello-servlet 我们就返回下面的东西
@WebServlet(name = "helloServlet", value = "/hello-servlet")
                                // HttpServlet is an abstract class by java to extend web site server
public class HelloServlet extends HttpServlet {
    private String message;

    public void init() {
        message = "Hello World!";
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // using jackson library to parse JSON, rather than manually parsing it
        response.setContentType("application/json");
        ObjectMapper mapper = new ObjectMapper();
        // store response in a Java object
        Customer customer= new Customer();
        customer.setEmail("sun@laioffer.com");
        customer.setPassword("123456");
        customer.setFirstName("rick");
        customer.setLastName("sun");
        customer.setEnabled(true);
        // turn java object into json string and set it back to front end
        response.getWriter().print(mapper.writeValueAsString(customer));
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        // jackson turning JSON string (in the request) to Java object
        Customer customer = mapper.readValue(IOUtils.toString(request.getReader()), Customer.class);
        System.out.println(customer.getEmail());
        System.out.println(customer.getFirstName());
        System.out.println(customer.getLastName());

        response.setContentType("application/json");
        JSONObject jsonResponse = new JSONObject();
        jsonResponse.put("status", "ok");
        response.getWriter().print(jsonResponse);
    }

    public void destroy() {
    }
}