package com.iqmsoft.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.iqmsoft.domain.Note;
import com.iqmsoft.repository.NoteRepository;

@Service
public class NoteService {

    private NoteRepository noteRepository;

    @Autowired
    public NoteService( NoteRepository noteRepository ) {
        this.noteRepository = noteRepository;
    }

    public List<Note> findAllNotes(){
        List<Note> notes = new ArrayList<>();
        noteRepository.findAll().forEach( notes::add );
        return notes;
    }

    public Note addNote(Note note){
        return noteRepository.save(note);
    }

    public void deleteNote(int id){
        noteRepository.deleteById( id );
    }

    public void updateNote(Note note){
        noteRepository.save(note);
    }

    public Note findOneNote(int id){
        return noteRepository.findById( id ).get();
    }

    public List<Note> findAllNotesByTitle(String title){
        return noteRepository.findAllNoteByTitleIgnoreCaseOrderByIdAsc( title );
    }

    public List<Note> findAllContentLike(String content){
        return noteRepository.findAllByContentLikeIgnoreCaseOrderByIdDesc( "%"+ content +"%" );
    }
}
