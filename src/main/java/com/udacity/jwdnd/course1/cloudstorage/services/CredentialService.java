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
    private List<Credential> credentials;
    private CredentialMapper credentialMapper;
    private EncryptionService encryptionService;

    public CredentialService(List<Credential> credentials, CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentials = credentials;
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    @PostConstruct
    public void postConstruct() {
        this.credentials = new ArrayList<>();
    }

    public int createCredential(Credential credential) {
        SecureRandom random = new SecureRandom();
        byte[] salt = new byte[16];
        random.nextBytes(salt);
        String encodedSalt = Base64.getEncoder().encodeToString(salt);
        String encryptedPassword = encryptionService.encryptValue(credential.getPassword(), encodedSalt);
        Credential tempCredential = new Credential(null, credential.getUrl(), credential.getUserName(),
                encodedSalt, encryptedPassword, credential.getUserId(), null);
        int numInsertedRows = credentialMapper.insertCredential(tempCredential);
        if (numInsertedRows > 0) {
            addCredential(tempCredential);
        }
        int noteId = tempCredential.getCredentialId();
        System.out.println("tempCredential:" + tempCredential);
        return noteId;
    }

    public String decryptPass(String data, String salt) {
        return encryptionService.decryptValue(data, salt);
    }

    public List<Credential> getCredentials() {
        System.out.println("FROM NOTELIST SERVICE");
        for (var credential : this.credentials) {
            System.out.println(credential);
        }
        return new ArrayList<>(this.credentials);

    }

    public List<Credential> getCredentialsPerUser(Integer userId) {
        List<Credential> list = credentialMapper.getCredentialPerUser(userId);
        for (var cred : list) {
            cred.setDecoded(encryptionService.decryptValue(cred.getPassword(), cred.getKey()));
        }
        for (var cred : list) {
            System.out.println("=====================================================================");
            System.out.println(cred);
            System.out.println("=====================================================================");

        }
        return list;
    }

    public Credential addCredential(Credential credential) {
        credentials.add(credential);
        return credential;
    }

    public int deleteCredential(Integer credentialId, Integer userId) {
        return credentialMapper.deleteCredential(credentialId, userId);
    }


        public Credential getCredential(Integer credentialId, Integer userId) {
        return credentialMapper.getCredential(credentialId, userId);
    }
    public int updateCredential(Integer credentialId, Integer userId, String url, String userName, String password) {
        return credentialMapper.updateCredential(credentialId, userId, url, userName, password);
    }

}
