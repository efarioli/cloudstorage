package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;


@Controller
public class CredentialController {
    private NoteService noteService;
    private NoteListService noteListService;
    private UserService userService;
    private CredentialService credentialService;
    private EncryptionService encryptionService;
    private FileService fileService;

    @Autowired
    public CredentialController(NoteService noteService, NoteListService noteListService, UserService userService, CredentialService credentialService, EncryptionService encryptionService, FileService fileService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
        this.userService = userService;
        this.credentialService = credentialService;
        this.encryptionService = encryptionService;
        this.fileService = fileService;
    }

    @PostMapping("/credential/add")
    public ModelAndView addCredential(@ModelAttribute("newCredential") CredentialForm credentialForm, BindingResult bindingResult) {

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();

        if (credentialForm.getCredentialId() != null) {
            System.out.println("UPDATE CREDENTIAL REQUEST");
            credentialForm.setUserId(user.getUserId());
            CredentialForm cf = credentialForm;
            System.out.println(cf);
            Credential c = credentialService.getCredential(cf.getCredentialId(), user.getUserId());
            String encryptedPassword = encryptionService.encryptValue(cf.getPassword(), c.getKey());


            credentialService.updateCredential(cf.getCredentialId(), cf.getUserId(), cf.getUrl(),
                    cf.getUserName(), encryptedPassword);
            //noteService.updateNote(noteForm.getNoteId(),user.getUserId(),noteForm.getNoteTitle(),noteForm.getNoteDescription());
        } else {
            System.out.println("ADD CREDENTIAL REQUEST");
            Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUserName(),
                    null, credentialForm.getPassword(), user.getUserId(), null);
            int rowAdded = credentialService.createCredential(credential);
        }
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "credential");
        return modelAndView;
    }

    @GetMapping(value = "/delete_credential/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, @ModelAttribute("newCredential") NoteForm noteForm, BindingResult bindingResult) {
        System.out.println("DELETE CREDENTIAL ROUTE");

        User user = CtrlHelper.getUserInfo(userService);

        int isRemoved = credentialService.deleteCredential(id, user.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "credential");

        return modelAndView;
    }
//    @GetMapping(value = "/credential/{id}")
//    public ModelAndView getCredential(@PathVariable Integer id, @ModelAttribute("newCredential") CredentialForm credentialForm, BindingResult bindingResult) {
//        System.out.println("CREDENTIAL DETAIL REQUEST");
//
//        User user = CtrlHelper.getUserInfo(userService);
//
//
//        Credential tempCredential = credentialService.getCredential(id, user.getUserId());
//        String decryptedPass = credentialService.decryptPass(tempCredential.getPassword(), tempCredential.getKey());
//        tempCredential.setPassword(decryptedPass);
//        ModelAndView modelAndView = new ModelAndView();
//        modelAndView.addObject("newNote", new Note());
//        modelAndView.addObject("newCredential", tempCredential);
//        CtrlHelper.setModelAndView(modelAndView,noteListService, credentialService,user, "note");
//
//        return modelAndView;
//    }

}
