package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.exception.FileStorageException;
import com.udacity.jwdnd.course1.cloudstorage.mapper.FileMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.File;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class FileService {
    private List<File> files;
    private FileMapper fileMapper;
    private UserService userService;

    @Autowired
    public FileService(List<File> files, FileMapper fileMapper, UserService userService) {
        this.files = files;
        this.fileMapper = fileMapper;
        this.userService = userService;
    }



    @PostConstruct
    public void postConstruct() {
        this.files = new ArrayList<>();
    }

    public Integer storeFile(File file){
        // Normalize file name


        // Check if the file's name contains invalid characters
        if(file.getFilename().contains("..")) {
            throw new FileStorageException("Sorry! Filename contains invalid path sequence " + file.getFilename());
        }

        int filesAdded = fileMapper.insertFile(file);
        getFiles();

        return filesAdded;

    }

    public List<File> getFiles() {
        System.out.println("FROM FILES LIST SERVICE");
        for (var file : this.files) {
            System.out.println(file);
        }
        return new ArrayList<>(this.files);

    }

    public List<File> getFilesPerUser(Integer userId) {
        List<File> list = fileMapper.getFilesPerUser(userId);

        for (var file: list) {
            System.out.println("=====================================================================");
            System.out.println(file.getFilename());
            System.out.println("=====================================================================");

        }
        files = list;
        return list;
    }

    public int deleteFile(Integer fileId, Integer userId) {
        return fileMapper.deleteFile(fileId, userId);
    }

    public File getFile(int fileId, Integer userId) {
        return fileMapper.getFile(fileId, userId);
    }
}
