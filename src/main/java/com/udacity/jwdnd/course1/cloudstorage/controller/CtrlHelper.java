package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

public  class CtrlHelper {
    public static User getUserInfo(UserService userService){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userService.getUser(userName);
    }
    public static ModelAndView setModelAndView(ModelAndView modelAndView, NoteListService noteListService, CredentialService credentialService, User user, String tabName){
        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));
        modelAndView.addObject("credentials", credentialService.getCredentialsPerUser(user.getUserId()));
        modelAndView.addObject("activeTabModel", tabName);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
