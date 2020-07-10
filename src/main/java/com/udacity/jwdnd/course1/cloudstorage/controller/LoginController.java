package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;


import java.util.Map;
import java.util.jar.JarOutputStream;

@Controller
public class LoginController {
    private final UserService userService;

    @Autowired
    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/login")

    public String loginView() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("Hited login GET");

        System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]" + auth.getName());
        

        if (!(auth instanceof AnonymousAuthenticationToken)) {
            System.out.println("*****************************************************************");
            System.out.println("]]]]]]]]]]]]]]]]]]]]]]]]]]" + auth.getName());
            //Integer userId = Integer.parseInt(auth.getName());
            //User user = userService.getUser(userId);
            System.out.println("#########################################");
            //System.out.println(user);
            // model.addAttribute("userModel", user);
            // System.out.println("44444" + model.getAttribute("userModel"));
            System.out.println("5555555555555555555555555555");


            /* The user is logged in :) */
            //return "redirect:/home";
            return "login";
            // return new ModelAndView("redirect:/home", (Map<String, ?>) model);
        }
        System.out.println("LOGIN ROUT-----------------------------------------------------");

        return "login";
        //return  new ModelAndView("login");
    }
}


