package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private NoteListService noteListService;
    private UserService userService;
    private CredentialService credentialService;

    public HomeController(NoteListService noteListService, UserService userService,
            CredentialService credentialService) {
        this.noteListService = noteListService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(@ModelAttribute("newMessage") NoteForm noteForm, Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        System.out.println("£££££££££££££££££££££££££££££££££££££££££££££££££££££££££");
        System.out.println(auth.getName());
        String userName = auth.getName();
        User user = userService.getUser(userName);
        noteForm.setUserId(user.getUserId());
        System.out.println(user);
        System.out.println("HOME ROUT-----------------------------------------------------");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newNote", new Note());
        modelAndView.addObject("newCredential", new Credential());
        modelAndView.addObject("newFile", new File());
        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));
        modelAndView.addObject("credentials", credentialService.getCredentialsPerUser(user.getUserId()));
        modelAndView.addObject("activeTabModel", "");
        modelAndView.setViewName("home");
        return modelAndView;
    }
    // @GetMapping("/logout")
    // public String doLogout(){
    // return "redirect:/login?logout";
    // }

}
