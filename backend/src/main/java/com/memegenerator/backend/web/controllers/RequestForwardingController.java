package com.memegenerator.backend.web.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


//This controller is here for internal redirecting from Angular on hosting sites like Heroku.
@Controller
public class RequestForwardingController {
    @RequestMapping(value = "/**/{path:[^\\.]*}")
    public String redirect() {
        // Forward to home page so that angular routing is preserved.
        return "forward:/";
    }
}