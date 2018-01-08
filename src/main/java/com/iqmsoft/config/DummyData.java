package com.iqmsoft.config;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.iqmsoft.domain.Note;
import com.iqmsoft.service.NoteService;

@Component
public class DummyData {

    private NoteService noteService;

    @Autowired
    public DummyData( NoteService noteService ) {
        this.noteService = noteService;
    }

    @PostConstruct
    private void createDummyData() {
        noteService.addNote( new Note( "head1", "content1", "date1" ) );
        noteService.addNote( new Note( "head2", "content2", "date2" ) );
        noteService.addNote( new Note( "head3", "content3", "date3" ) );
        noteService.addNote( new Note( "head4", "content4", "date4" ) );
        noteService.addNote( new Note( "head5", "content5", "date5" ) );
        noteService.addNote( new Note( "head6", "content6", "date6" ) );
        noteService.addNote( new Note( "head77", "content7", "date7" ) );
    }
}
