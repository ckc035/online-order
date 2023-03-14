package com.laioffer.onlineorder.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

// 这里写的是sign in failure response. success就不用写了
@Controller
public class SignInController {

    // json format
    private final ObjectMapper objectMapper = new ObjectMapper();

    // we only process the failed login request here, if login successfully, it will automatically redirect to home page
    // SercurityConfig will forward error to here
    @RequestMapping(value="/login")
    public void login(@RequestParam(value = "error") String error, HttpServletResponse response) throws IOException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        Map<String, Object> data = new HashMap<>();
        data.put("message", "bad credentials");
        response.getOutputStream()
                .println(objectMapper.writeValueAsString(data));
    }
}

