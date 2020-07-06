package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
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

        ModelAndView modelAndView = new ModelAndView();

        Note tempNote = new Note( null, noteForm.getNoteTitle(), noteForm.getNoteDescription(),noteForm.getUserId());
        modelAndView.addObject("notesModel", noteService.createNote(tempNote));
        modelAndView.addObject("getNotes", noteListService.getNotes());

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

}
