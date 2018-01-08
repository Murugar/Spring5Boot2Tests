package com.iqmsoft.web;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.iqmsoft.domain.Note;
import com.iqmsoft.service.NoteService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;


@Api(description = "API for notes")
@RestController
@RequestMapping("/api/notes")
public class NoteController {

    private NoteService noteService;

    @Autowired
    public NoteController( NoteService noteService ) {
        this.noteService = noteService;
    }

    @ApiOperation( value = "Find all notes")
    @GetMapping(value = "")
    public ResponseEntity<List<Note>> findAllNotes(){
        List<Note> notes = noteService.findAllNotes();
        Boolean isNoteEmpty = notes.toString().equals( "[]");
        ResponseEntity noteEmpty = new ResponseEntity( HttpStatus.NO_CONTENT);
        ResponseEntity<List<Note>> noteNotEmpty = new ResponseEntity<>( notes, HttpStatus.FOUND );
        return isNoteEmpty ? noteEmpty : noteNotEmpty;
    }

    @ApiOperation( value = "Find one note by ID")
    @GetMapping(value ="/{id}")
    public ResponseEntity<Note> findOneNote(@PathVariable int id){
        Boolean isNoteDoesntExist= noteService.findOneNote(id) == null;
        ResponseEntity noteNotFound = new ResponseEntity(HttpStatus.NOT_FOUND );
        ResponseEntity<Note> noteFound = new ResponseEntity<>(noteService.findOneNote(id), HttpStatus.FOUND );
        return isNoteDoesntExist ? noteNotFound : (ResponseEntity) noteFound;
    }

    @ApiOperation( value = "Find all note by TITLE(ASC)")
    @GetMapping(value = "title/{title}")
    public ResponseEntity<List<Note>> findAllNotesByTitle(@PathVariable String title){
        List<Note> responseBody = noteService.findAllNotesByTitle(title);
        Boolean isNoteDoesntExist= responseBody.toString().equals( "[]" );
        ResponseEntity noteNotFound =new ResponseEntity(HttpStatus.NOT_FOUND );
        ResponseEntity<List<Note>> noteFound = new ResponseEntity<>(responseBody, HttpStatus.FOUND );
        return isNoteDoesntExist ? noteNotFound : noteFound;
    }

    @ApiOperation( value = "Find all note by CONTENT(KEYWORD,ASC)")
    @GetMapping("content/{content}")
    public ResponseEntity<List<Note>> findAllNotesByContent(@PathVariable String content){
        List<Note> responseBody = noteService.findAllContentLike( content );
        Boolean isNoteEmpty = responseBody.toString().equals( "[]" );
        ResponseEntity noteEmpty = new ResponseEntity( HttpStatus.NO_CONTENT );
        ResponseEntity<List<Note>> noteNotEmpty= new ResponseEntity<>( responseBody, HttpStatus.FOUND );
        return isNoteEmpty ? noteEmpty : noteNotEmpty;
    }


    @ApiOperation( value = "Add note")
    @PostMapping
    public ResponseEntity<Note> addNote(@RequestBody Note note){
        Note result = noteService.addNote(note);
        Boolean isNoteFound = findOneNote(result.getId()) != null;
        ResponseEntity<Note> noteFound = new ResponseEntity<>(result,HttpStatus.CREATED);
        ResponseEntity noteNotFound = new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        return isNoteFound ? noteFound : noteNotFound;
    }


    @ApiOperation( value="Update note" )
    @PutMapping("/{id}")
    public ResponseEntity<Note> updateNote(@PathVariable int id, @RequestBody Note note){
        Boolean isNoteDoesntExist= noteService.findOneNote( id ) == null;
        if ( isNoteDoesntExist ){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST );
        }else{
            noteService.updateNote(note);
            return new ResponseEntity<>(noteService.findOneNote( id ), HttpStatus.OK );
        }
    }

    @ApiOperation( value="Delete note" )
    @DeleteMapping("/{id}")
    public ResponseEntity deleteNote(@PathVariable int id){
        Boolean isNoteDoesntExist= noteService.findOneNote( id ) == null;
        if ( isNoteDoesntExist ){
            return new ResponseEntity( HttpStatus.BAD_REQUEST );
        }else{
            noteService.deleteNote( id );
            return new ResponseEntity( HttpStatus.OK );
        }
    }


}
