package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class HomeController {
    private NoteService noteService;
    private NoteListService noteListService;
    private UserService userService;

    public HomeController(NoteService noteService, NoteListService noteListService, UserService userService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
        this.userService = userService;
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
        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));
        modelAndView.addObject("addedObjectModel", "");
        modelAndView.setViewName("home");
        return modelAndView;
    }
//    @GetMapping("/logout")
//    public String doLogout(){
//        return "redirect:/login?logout";
//    }



}
