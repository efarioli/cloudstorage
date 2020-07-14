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
    @GetMapping("/note/add")
    public ModelAndView justRedirect(@ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();

        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "note");
        return modelAndView;
    }

    @PostMapping("/note/add")
    public ModelAndView addNote(@ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();
        int rowUpdated = 0;
        int rowAdded = 0;

        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "note");

        if (noteForm.getNoteId() != null) {
            System.out.println("UPDATE NOTE REQUEST");
            rowUpdated = noteService.updateNote(noteForm.getNoteId(), user.getUserId(), noteForm.getNoteTitle(), noteForm.getNoteDescription());
            System.out.println("----------rowupdated--" + rowUpdated);
            if (rowUpdated==1){
                modelAndView.addObject("message", "A Note has been updated..");
                modelAndView.addObject("error", false);
            } else {
                modelAndView.addObject("message", "Something went wrong... Your file has NOT been updated");
                modelAndView.addObject("error", true);
            }
        } else {
            System.out.println("ADD NOTE REQUEST");

            Note tempNote = new Note(null, noteForm.getNoteTitle(), noteForm.getNoteDescription(), user.getUserId());
            rowAdded = noteService.createNote(tempNote);
            System.out.println("----------rowadded--" + rowAdded);
            if (rowAdded>0){
                modelAndView.addObject("message", "A Note has been added..");
                modelAndView.addObject("error", false);
            } else {
                modelAndView.addObject("message", "Something went wrong... Your note has NOT been added");
                modelAndView.addObject("error", true);
            }

        }



        modelAndView.addObject("showModal", true);
        modelAndView.addObject("getNotes", noteListService.getNotesPerUser(user.getUserId()));


        return modelAndView;
    }

    @GetMapping(value = "/delete_note/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, @ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {
        System.out.println("DELETE ROUTE");

        User user = CtrlHelper.getUserInfo(userService);

        int isRemoved = noteService.deleteNote(id, user.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "note");
        if (isRemoved==1){
            modelAndView.addObject("message", "A Note has been deleted..");
            modelAndView.addObject("error", false);
        } else {
            modelAndView.addObject("message", "Something went wrong... Your note has NOT been deleted");
            modelAndView.addObject("error", true);
        }
        modelAndView.addObject("showModal", true);

        return modelAndView;
    }

}
