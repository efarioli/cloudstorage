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

@Controller
@RequestMapping("/home")
public class HomeController {
    private NoteService noteService;
    private NoteListService noteListService;

    public HomeController(NoteService noteService, NoteListService noteListService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
    }

    @GetMapping()
    public String getHomePage() {
        System.out.println("HOME ROUT-----------------------------------------------------");


        return "home";
    }



}
