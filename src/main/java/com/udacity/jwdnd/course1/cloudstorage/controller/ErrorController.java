package com.udacity.jwdnd.course1.cloudstorage.controller;


import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

@Controller
class MyCustomErrorController implements ErrorController {

    //error 404, 405, ect are redirect to /home if the user is logged in, otherwise redirect to login page
    @RequestMapping("/error")
    public String handleError(HttpServletRequest request) {
        Integer statusCode = (Integer) request.getAttribute("javax.servlet.error.status_code");
        Exception exception = (Exception) request.getAttribute("javax.servlet.error.exception");
        return "redirect:/home";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
