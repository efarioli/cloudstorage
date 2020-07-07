package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.NoteMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteListService {
    private List<Note> notes;
    private NoteMapper noteMapper;

    public NoteListService(List<Note> notes, NoteMapper noteMapper) {
        this.notes = notes;
        this.noteMapper = noteMapper;
    }

    @PostConstruct
    public  void postConstruct() {
        this.notes = new ArrayList<>();
    }
    public Note addNote(Note note){
        notes.add(note);
        return note;
    }
    public List<Note> getNotes()
    {
        System.out.println("FROM NOTELIST SERVICE");
        for(var note : this.notes){
            System.out.println(note);
        }
        return new ArrayList<>(this.notes);

    }
    public List<Note> getNotesPerUser(Integer userId){
        return noteMapper.getNotesPerUser(userId);
    }
}
