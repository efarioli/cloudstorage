package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoteController {
    private NoteService noteService;
    private NoteListService noteListService;
    private UserService userService;

    public NoteController(NoteService noteService, NoteListService noteListService, UserService userService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
        this.userService = userService;
    }

    @PostMapping("/note/add")
    public ModelAndView addNote(@ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {

        User user = getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();

        if (noteForm.getNoteId() != null) {
            System.out.println("UPDATE NOTE REQUEST");
            noteService.updateNote(noteForm.getNoteId(),user.getUserId(),noteForm.getNoteTitle(),noteForm.getNoteDescription());
        } else {
            System.out.println("ADD NOTE REQUEST");

            Note tempNote = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user.getUserId());
            noteService.createNote(tempNote);
        }
        setModelAndView(modelAndView,noteListService,user, "note");

        return modelAndView;
    }

    @GetMapping(value = "/delete_note/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, @ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {
        System.out.println("DELETE ROUTE");

        User user = getUserInfo(userService);

        int isRemoved = noteService.deleteNote(id, user.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        setModelAndView(modelAndView,noteListService,user, "note");

        return modelAndView;
    }

    @GetMapping(value = "/note/{id}")
    public ModelAndView getNote(@PathVariable Integer id, @ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {
        System.out.println("NOTE DETAIL REQUEST");

        User user = getUserInfo(userService);


        Note tempNote = noteService.getNote(id, user.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("newNote", tempNote);
        return modelAndView;
    }

    public static User  getUserInfo(UserService userService){
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        return userService.getUser(userName);
    }
    public static  ModelAndView setModelAndView(ModelAndView modelAndView, NoteListService noteListService, User user, String tabName){
        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));
        modelAndView.addObject("activeTabModel", tabName);
        modelAndView.setViewName("home");
        return modelAndView;
    }
}
