package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

@Service
public class NoteListService {
    private List<Note> notes;

    @PostConstruct
    public  void postConstruct() {
        this.notes = new ArrayList<>();
    }
    public Note addNote(Note note){
        notes.add(note);
        return note;
    }
    public List<Note> getNotes(){
        return new ArrayList<>(this.notes);
    }
}
