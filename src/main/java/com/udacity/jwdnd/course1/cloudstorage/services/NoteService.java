package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.mapper.UserMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;

@Service
public class NoteService {
    private final UserMapper userMapper;
    private final NoteMapper noteMapper;
    private final NoteListService noteListService;

    public NoteService(UserMapper userMapper, NoteMapper noteMapper, NoteListService noteListService) {
        this.userMapper = userMapper;
        this.noteMapper = noteMapper;
        this.noteListService = noteListService;
    }

    public boolean isUsernameAvailable(String username) {
        return userMapper.getUser(username) == null;
    }

    public int createNote(Note note) {
        Note tempNote = new Note(null, note.getNoteTitle(), note.getNoteDescription(), note.getUserId());
        int numInsertedRows= noteMapper.insert(tempNote);
        if  (numInsertedRows>0){
            noteListService.addNote(tempNote);
        }
        int noteId = tempNote.getNoteId();
        System.out.println("tempnote:" + tempNote);
        return noteId;
    }
    public Note getNote (Integer noteId, Integer userId) {return noteMapper.getNote(noteId, userId);}

    public int deleteNote(Integer noteId, Integer userId) {return  noteMapper.deleteNote(noteId,userId);}

    public int updateNote(Integer noteId, Integer userId, String noteTitle, String noteDescription) {return  noteMapper.updateNote(noteId,userId, noteTitle, noteDescription);}

}
