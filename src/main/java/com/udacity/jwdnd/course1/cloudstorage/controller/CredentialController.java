package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;

@Controller
public class CredentialController {
    private NoteService noteService;
    private NoteListService noteListService;
    private UserService userService;
    private CredentialService credentialService;

    public CredentialController(NoteService noteService, NoteListService noteListService, UserService userService, CredentialService credentialService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
        this.userService = userService;
        this.credentialService = credentialService;
    }

    @PostMapping("/credential/add")
    public ModelAndView addCredential(@ModelAttribute("newCredential") CredentialForm credentialForm, BindingResult bindingResult) {

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();

        if (credentialForm.getCredentialId() != null) {
            System.out.println("UPDATE CREDENTIAL REQUEST");
            //noteService.updateNote(noteForm.getNoteId(),user.getUserId(),noteForm.getNoteTitle(),noteForm.getNoteDescription());
        } else {
            System.out.println("ADD CREDENTIAL REQUEST");

            Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUserName(),
                    null, credentialForm.getPassword(), user.getUserId());
            int rowAdded = credentialService.createCredential(credential);
        }
        CtrlHelper.setModelAndView(modelAndView,noteListService, credentialService,user, "note");
        modelAndView.addObject("newNote", new Note());
       // modelAndView.addObject("newCredential", new Credential());
        return modelAndView;
    }

    @GetMapping(value = "/delete_credential/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, @ModelAttribute("newCredential") NoteForm noteForm, BindingResult bindingResult) {
        System.out.println("DELETE CREDENTIAL ROUTE");

        User user = CtrlHelper.getUserInfo(userService);

        int isRemoved = credentialService.deleteCredential(id, user.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView,noteListService, credentialService,user, "note");
        modelAndView.addObject("newNote", new Note());
        modelAndView.addObject("newCredential", new Credential());


        return modelAndView;
    }







}
