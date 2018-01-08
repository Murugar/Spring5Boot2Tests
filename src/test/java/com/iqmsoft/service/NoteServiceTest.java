package com.iqmsoft.service;

import static org.junit.Assert.assertEquals;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.junit.Before;
import org.junit.Test;
import org.yaml.snakeyaml.introspector.PropertyUtils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqmsoft.domain.Note;
import com.jayway.jsonpath.JsonPath;


public class NoteServiceTest {

    private List<Note> notes = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp(){
        //not using spring data test so no access to auto inc in id
        notes.add( new Note( 1,"title1","content1","date1" ) );
        notes.add( new Note( 2,"title2","content2","date2" ) );
        notes.add( new Note( 3,"title3","content3","date3" ) );
        notes.add( new Note( 4,"title4","content4","date4" ) );
        notes.add( new Note( 5,"title5","content5","date5" ) );
    }

    @Test
    public void findAllNotes() throws Exception {
        int result = notes.size();
        assertEquals("fail: expected note not null",5,result);
    }

    @Test
    public void addNote() throws Exception {
        notes.add( new Note( "title6","content6","date6" ) );
        int result = notes.size();
        assertEquals( "fail: note not added", 6, result );
    }

    @Test
    public void updateNote() throws Exception {
        notes.set( 0, new Note( 1,"edited","edited","edited" ) );
        String result = objectMapper.writeValueAsString( new Note( 1,"edited","edited","edited" ) );
        String expected = objectMapper.writeValueAsString( notes.get( 0 ) );
        assertEquals( "fail: note not updated", expected, result );
    }

    @Test
    public void findOneNote() throws Exception {
        String result = objectMapper.writeValueAsString( notes.get( 0 ) ) ;
        String expected = objectMapper.writeValueAsString( new Note( 1,"title1","content1","date1" ) );
        assertEquals( "fail: note not find",expected, result );
    }

    @Test
    public void deleteNote() throws Exception {
        notes.remove(0 );
        String expected = objectMapper.writeValueAsString( notes.get( 0 ) );
        String result = objectMapper.writeValueAsString(new Note( 2,"title2","content2","date2" ) );
        assertEquals( "fail: note not deleted", expected , result);
    }

    @Test
    public void findAllContentLike() throws Exception {

    }

    @Test
    public void findAllNotesByTitle() throws Exception {

    }
}
