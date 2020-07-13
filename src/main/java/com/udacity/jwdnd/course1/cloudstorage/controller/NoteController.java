package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class NoteController {
    private NoteService noteService;
    private NoteListService noteListService;
    private UserService userService;
    private CredentialService credentialService;
    private FileService fileService;

    @Autowired
    public NoteController(NoteService noteService, NoteListService noteListService, UserService userService, CredentialService credentialService, FileService fileService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }

    @PostMapping("/note/add")
    public ModelAndView addNote(@ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();

        if (noteForm.getNoteId() != null) {
            System.out.println("UPDATE NOTE REQUEST");
            noteService.updateNote(noteForm.getNoteId(), user.getUserId(), noteForm.getNoteTitle(), noteForm.getNoteDescription());
        } else {
            System.out.println("ADD NOTE REQUEST");

            Note tempNote = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user.getUserId());
            noteService.createNote(tempNote);
        }
        modelAndView.addObject("newCredential", new Credential());
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "note");

        return modelAndView;
    }

    @GetMapping(value = "/delete_note/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, @ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {
        System.out.println("DELETE ROUTE");

        User user = CtrlHelper.getUserInfo(userService);

        int isRemoved = noteService.deleteNote(id, user.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "note");

        return modelAndView;
    }

}
