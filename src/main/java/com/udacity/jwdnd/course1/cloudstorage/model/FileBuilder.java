package com.udacity.jwdnd.course1.cloudstorage.model;

import java.sql.Blob;

public class FileBuilder {
    private Integer fileId;
    private String filename;
    private String contentType;
    private Integer userId;
    private Blob fileData;

    public FileBuilder setFileId(Integer fileId) {
        this.fileId = fileId;
        return this;
    }

    public FileBuilder setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public FileBuilder setContentType(String contentType) {
        this.contentType = contentType;
        return this;
    }

    public FileBuilder setUserId(Integer userId) {
        this.userId = userId;
        return this;
    }

    public FileBuilder setFileData(Blob fileData) {
        this.fileData = fileData;
        return this;
    }

    public File createFile() {
        return new File(fileId, filename, contentType, userId, fileData);
    }
}