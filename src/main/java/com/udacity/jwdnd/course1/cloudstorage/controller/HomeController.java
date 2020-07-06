package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
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

    public HomeController(NoteService noteService, NoteListService noteListService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
    }

    @GetMapping("/home")
    public ModelAndView getHomePage(@ModelAttribute("newMessage") NoteForm noteForm, Model model) {
        System.out.println("HOME ROUT-----------------------------------------------------");
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newNote", new Note());
        modelAndView.addObject("getNotes", noteListService.getNotes());
        modelAndView.addObject("addedObjectModel", "");
        modelAndView.setViewName("/home");
        return modelAndView;
    }
//    @GetMapping("/logout")
//    public String doLogout(){
//        return "redirect:/login?logout";
//    }



}
