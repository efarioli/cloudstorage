package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.apache.ibatis.annotations.Delete;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
//        if(bindingResult.hasErrors()){
//            System.out.println("There was a error "+bindingResult);
//            System.out.println("Person is: "+ noteForm.getNoteDescription());
//            return "index";
//        }
        //System.out.println(model.getAttribute(""));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.getUser(userName);



        ModelAndView modelAndView = new ModelAndView();

        Note tempNote = new Note( null, noteForm.getNoteTitle(), noteForm.getNoteDescription(),user.getUserId());
        modelAndView.addObject("notesModel", noteService.createNote(tempNote));
        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));

        System.out.println(tempNote);

       // noteListService.addNote((tempNote) model.getAttribute("notesModel"));
        noteForm.setNoteTitle("");
        noteForm.setNoteDescription("");
//        if(bindingResult.hasErrors()){
//            System.out.println("There was a error "+bindingResult);
//            System.out.println("Person is: "+ tempNote.getNoteDescription());
//            return "index";
//        }
        modelAndView.addObject("addedObjectModel", "note");
        modelAndView.setViewName("home");
        //modelAndView.setViewName("redirect:/home");

        return modelAndView;
    }

    @GetMapping(value = "/delete_note/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, @ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {

        System.out.println("DELETE ROUTE");



//        if (isRemoved>0) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }



        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String userName = auth.getName();
        User user = userService.getUser(userName);

        int isRemoved = noteService.deleteNote(id, user.getUserId());

        ModelAndView modelAndView = new ModelAndView();

        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));

        modelAndView.addObject("addedObjectModel", "note");
        modelAndView.setViewName("home");

        return modelAndView;
    }


}
