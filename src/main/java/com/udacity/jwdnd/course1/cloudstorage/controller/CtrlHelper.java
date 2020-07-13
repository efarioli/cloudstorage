package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import java.lang.reflect.Method;

public  class CtrlHelper {
    public static User getUserInfo(UserService userService){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userService.getUser(userName);
    }
    public static void  setModelAndView(ModelAndView modelAndView, NoteListService noteListService, CredentialService credentialService, FileService fileService, User user, String tabName){
        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));
        modelAndView.addObject("credentials", credentialService.getCredentialsPerUser(user.getUserId()));
        modelAndView.addObject("getFiles", fileService.getFilesPerUser(user.getUserId()));
        modelAndView.addObject("activeTabModel", tabName);
        modelAndView.addObject("newNote", new Note());
        modelAndView.addObject("newCredential", new Credential());
        modelAndView.addObject("activeTabModel", tabName);
        modelAndView.addObject("userName", user.getFirstName() + " " + user.getLastName());
        modelAndView.setViewName("home");
    }
}
