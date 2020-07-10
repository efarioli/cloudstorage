package com.udacity.jwdnd.course1.cloudstorage.controller;



import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import com.udacity.jwdnd.course1.cloudstorage.services.CredentialService;
import com.udacity.jwdnd.course1.cloudstorage.services.FileService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.IOException;

@Controller
public class FileController {
    private UserService userService;
    private NoteListService noteListService;
    private CredentialService credentialService;

    private FileService fileService;


    @Autowired
    public FileController(UserService userService, NoteListService noteListService, CredentialService credentialService, FileService fileService) {
        this.userService = userService;
        this.noteListService = noteListService;
        this.credentialService = credentialService;
        this.fileService = fileService;
    }


    @PostMapping("/file/add")
    public ModelAndView addFile(@RequestParam("newFile") MultipartFile newFile) throws IOException {
        System.out.println("ADD FILE HITTED");

        User user = CtrlHelper.getUserInfo(userService);

        ModelAndView modelAndView = new ModelAndView();
        //modelAndView.addObject("newFile", )
        System.out.println("+++++" + newFile);
        File tempFile = new File(newFile);
        tempFile.setUserId(user.getUserId());


        int added = fileService.storeFile(tempFile);
        System.out.println("addded " + added);
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "note");

        modelAndView.addObject("newFile", new File());
        modelAndView.addObject("newNote", new Note());
        modelAndView.addObject("newCredential", new Credential());


        modelAndView.setViewName("home");
        System.out.println("END -- ADD FILE HITTED");
        return modelAndView;


    }

    @GetMapping(value = "/delete_file/{id}")
    public ModelAndView deleteFile(@PathVariable Integer id) {
        System.out.println("DELETE FILE ROUTE");

        User user = CtrlHelper.getUserInfo(userService);

        int isRemoved = fileService.deleteFile(id, user.getUserId());

        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "note");
        modelAndView.addObject("newFile", new File());
        modelAndView.addObject("newNote", new Note());
        modelAndView.addObject("newCredential", new Credential());

        return modelAndView;
    }
}
