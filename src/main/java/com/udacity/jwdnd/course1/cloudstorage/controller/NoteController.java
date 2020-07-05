package com.udacity.jwdnd.course1.cloudstorage.controller;

import com.udacity.jwdnd.course1.cloudstorage.model.Note;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteListService;
import com.udacity.jwdnd.course1.cloudstorage.services.NoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class NoteController {
    private NoteService noteService;
    private NoteListService noteListService;

    public NoteController(NoteService noteService, NoteListService noteListService) {
        this.noteService = noteService;
        this.noteListService = noteListService;
    }

    @PostMapping("/note/new")
    public String addNote(@ModelAttribute("newNote") NoteForm noteForm, BindingResult bindingResult, Model model) {
        if(bindingResult.hasErrors()){
            System.out.println("There was a error "+bindingResult);
            System.out.println("Person is: "+ noteForm.getNoteDescription());
            return "index";
        }
        Note tempNote = new Note( null, noteForm.getNoteTitle(), noteForm.getNoteDescription(),noteForm.getUserId());
        model.addAttribute("notesModel", noteService.createNote(new Note( null, noteForm.getNoteTitle(), noteForm.getNoteDescription(),noteForm.getUserId())));
        noteListService.addNote((Note) model.getAttribute("notesModel"));
        noteForm.setNoteTitle("");
        noteForm.setNoteDescription("");
        if(bindingResult.hasErrors()){
            System.out.println("There was a error "+bindingResult);
            System.out.println("Person is: "+ tempNote.getNoteDescription());
            return "index";
        }
        return "home";
    }

}
