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
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

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
        System.out.println("ADD FILE HIT");
        ModelAndView modelAndView = new ModelAndView();
        User user = CtrlHelper.getUserInfo(userService);


        if (newFile.getOriginalFilename().equals("")){
            CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "file");
            modelAndView.addObject("activeTabModel", "file");
            modelAndView.addObject("message", "You must provide a file...\nPlease select a file");
            modelAndView.addObject("error", true);
            modelAndView.addObject("showModal", true);

            modelAndView.setViewName("home");
            return modelAndView;
        }

        //Check id the filename exists in the list of file
        //If it exists, cancel the upload of the file and show an error message
        boolean isFilenameInList = fileService.isFileNameExist( newFile.getResource().getFilename());
        if (isFilenameInList){
            CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "file");
            modelAndView.addObject("message", "The File has not been "+ "added" + "...\n" +
                    "Another file has already the same filename. It cannot be two files with the same filename. Please Choose another file and try again.");
            modelAndView.addObject("error", true);
            modelAndView.addObject("showModal", true);
            return modelAndView;

        }


        //modelAndView.addObject("newFile", )
        System.out.println("+++++" + newFile);
        System.out.println("+++++" + newFile.getOriginalFilename());
        System.out.println("+++++" + (newFile.getBytes()==null) + "---" + newFile.getSize() + "----(" + newFile.getOriginalFilename() + ")--filename--" + (newFile.getOriginalFilename().equals("")) + (newFile.getOriginalFilename()==null)) ;

        File tempFile = new File(newFile);
        tempFile.setUserId(user.getUserId());


        int added = fileService.storeFile(tempFile);
        System.out.println("addded " + added);
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "file");
        modelAndView.addObject("message", "A file has been added..");
        modelAndView.addObject("error", false);
        modelAndView.addObject("showModal", true);

        return modelAndView;
    }

    @GetMapping(value = "/delete_file/{id}")
    public ModelAndView deleteFile(@PathVariable Integer id) {
        System.out.println("DELETE FILE ROUTE");

        User user = CtrlHelper.getUserInfo(userService);
        int isRemoved = fileService.deleteFile(id, user.getUserId());
        System.out.println("is removed----" + isRemoved);

        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "file");
        if (isRemoved==1){
            modelAndView.addObject("message", "A file has been deleted..");
            modelAndView.addObject("error", false);
        } else {
            modelAndView.addObject("message", "Something went wrong... Your file has NOT been deleted");
            modelAndView.addObject("error", true);
        }
        modelAndView.addObject("showModal", true);
        return modelAndView;
    }

    @GetMapping("/download_file/{fileId}")
    public <T> ResponseEntity<T> downloadFIle(@PathVariable String fileId) {
        // Load file from database
        User user = CtrlHelper.getUserInfo(userService);
        File file = fileService.getFile(Integer.parseInt(fileId), user.getUserId());
        ModelAndView modelAndView = new ModelAndView();
        CtrlHelper.setModelAndView(modelAndView, noteListService, credentialService, fileService, user, "file");

        return (ResponseEntity<T>) ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(file.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFilename() + "\"")
                .body(new ByteArrayResource(file.getFileData()));
    }
}
