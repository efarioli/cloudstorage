package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private  List<Credential> credentials;
    private  CredentialMapper credentialMapper;
    private HashService hashService;

    public CredentialService(List<Credential> credentials, CredentialMapper credentialMapper, HashService hashService) {
        this.credentials = credentials;
        this.credentialMapper = credentialMapper;
        this.hashService = hashService;
    }

    @PostConstruct
    public  void postConstruct() {
        this.credentials = new ArrayList<>();
    }

    public int createCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String hashedPassword = hashService.getHashedValue(credential.getPassword(), encodedSalt);
        Credential tempCredential = new Credential(null, credential.getUrl(), credential.getUserName(),
                encodedSalt, hashedPassword, credential.getUserId());
        int numInsertedRows= credentialMapper.insertCredential(tempCredential);
        if  (numInsertedRows>0){
            addCredential(tempCredential);
        }
        int noteId = tempCredential.getCredentialId();
        System.out.println("tempCredential:" + tempCredential);
        return noteId;
    }
    public List<Credential> getCredentials()
    {
        System.out.println("FROM NOTELIST SERVICE");
        for(var credential : this.credentials){
            System.out.println(credential);
        }
        return new ArrayList<>(this.credentials);

    }
    public List<Credential> getCredentialsPerUser(Integer userId){
        return credentialMapper.getCredentialPerUser(userId);
    }
    public Credential addCredential(Credential credential){
        credentials.add(credential);
        return credential;
    }
    public int deleteCredential(Integer credentialId, Integer userId) {return  credentialMapper.deleteCredential(credentialId,userId);}


}
