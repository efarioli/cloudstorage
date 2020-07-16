package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
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
    @GetMapping("/credential/add")
    public ModelAndView justRedirect(@ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult) {

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();

        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "credential");
        return modelAndView;
    }

    @PostMapping("/credential/add")
    public ModelAndView addCredential(@ModelAttribute("newCredential") CredentialForm credentialForm, BindingResult bindingResult) {

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();
        int rowUpdated = 0;
        int rowAdded = 0;
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "credential");
        boolean isUsernameInList = credentialService.isUsernameExist(credentialForm.getUserName(),credentialForm.getUserId());
        if (isUsernameInList){
            String word = (credentialForm.getCredentialId() != null)? "updated": "added";
            modelAndView.addObject("message", "The Credential has not been "+ word + "...\n" +
                    "Another credential has already the same username. It cannot be two credentials with the same user name. Please Choose another username and try again.");
            modelAndView.addObject("error", true);
            modelAndView.addObject("showModal", true);
            return modelAndView;

        }


        if (credentialForm.getCredentialId() != null) {
            System.out.println("UPDATE CREDENTIAL REQUEST");
            credentialForm.setUserId(user.getUserId());
            CredentialForm cf = credentialForm;
            System.out.println(cf);
            Credential c = credentialService.getCredential(cf.getCredentialId(), user.getUserId());
            String encryptedPassword = encryptionService.encryptValue(cf.getPassword(), c.getKey());


            rowUpdated = credentialService.updateCredential(cf.getCredentialId(), cf.getUserId(), cf.getUrl(),
                    cf.getUserName(), encryptedPassword);
            if (rowUpdated==1){
                modelAndView.addObject("message", "A Credential has been updated..");
                modelAndView.addObject("error", false);
            } else {
                modelAndView.addObject("message", "Something went wrong... Your credential has NOT been updated");
                modelAndView.addObject("error", true);
            }
        } else {
            System.out.println("ADD CREDENTIAL REQUEST");
            Credential credential = new Credential(null, credentialForm.getUrl(), credentialForm.getUserName(),
                    null, credentialForm.getPassword(), user.getUserId(), null);
            rowAdded = credentialService.createCredential(credential);
            if (rowAdded>0){
                modelAndView.addObject("message", "A Credential has been added..");
                modelAndView.addObject("error", false);
            } else {
                modelAndView.addObject("message", "Something went wrong... Your credential has NOT been added");
                modelAndView.addObject("error", true);
            }
        }
        modelAndView.addObject("credentials", credentialService.getCredentialsPerUser(user.getUserId()));
        modelAndView.addObject("showModal", true);


        return modelAndView;
    }

    @GetMapping(value = "/delete_credential/{id}")
    public ModelAndView deleteNote(@PathVariable Integer id, @ModelAttribute("newCredential") NoteForm noteForm, BindingResult bindingResult) {
        System.out.println("DELETE CREDENTIAL ROUTE");

        User user = CtrlHelper.getUserInfo(userService);

        int isRemoved = credentialService.deleteCredential(id, user.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "credential");
        if (isRemoved==1){
            modelAndView.addObject("message", "A Credential has been deleted..");
            modelAndView.addObject("error", false);
        } else {
            modelAndView.addObject("message", "Something went wrong... Your Credential has NOT been deleted");
            modelAndView.addObject("error", true);
        }
        modelAndView.addObject("showModal", true);

        return modelAndView;
    }

}
