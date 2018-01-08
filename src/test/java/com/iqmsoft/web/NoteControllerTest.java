package com.iqmsoft.web;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.iqmsoft.domain.Note;
import com.iqmsoft.service.NoteService;
import com.iqmsoft.web.NoteController;

import org.aspectj.weaver.ast.Not;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;



@RunWith(SpringRunner.class)
@WebMvcTest(NoteController.class)
public class NoteControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private NoteService service;

    private List<Note> notes = new ArrayList<>();
    private ObjectMapper objectMapper = new ObjectMapper();

    @Before
    public void setUp(){
        notes.add(new Note( 1,"title1","content1","date1" ));
    }


    @Test
    public void findAllNotes() throws Exception {

        Mockito.when(service.findAllNotes()).thenReturn(notes);
        MvcResult result = mvc.perform(get( "/api/notes" )
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andExpect(jsonPath( "$", hasSize(1)))
                .andReturn();
        assertEquals("fail: expect response not null", result.getResponse().getContentAsString(), objectMapper.writeValueAsString(notes));
    }

    @Test
    public void findOneNote() throws Exception {
        Mockito.when(service.findOneNote(Mockito.anyInt())).thenReturn(notes.get( 0 ));
        MvcResult result = mvc.perform(get( "/api/notes/1" )
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();
    }

    @Test
    public void findAllNotesByTitle() throws Exception {
        notes.add( new Note( 2,"title2","content2","date2" ) );
        notes.add( new Note( 3,"title2","content3","date3" ) );
        notes.add( new Note( 4,"title2","content3","date3" ) );

        List<Note> noteContainsTitle2 = notes.stream().filter( n -> "title2".equals( n.getTitle() ) ).collect( Collectors.toList());

        Mockito.when( service.findAllNotesByTitle(Mockito.anyString())).thenReturn( ( noteContainsTitle2 ));
        MvcResult result = mvc.perform(get( "/api/notes/title/title2" )
                .contentType( MediaType.APPLICATION_JSON))
                .andExpect(status().isFound())
                .andReturn();
        assertEquals("fail: expect response not null", result.getResponse().getContentAsString(), objectMapper.writeValueAsString(noteContainsTitle2));
    }

    @Test
    public void addNote() throws Exception {
        Note sampleNoteData = new Note( 1,"title1","content1","date1" );

        String jsonInput = objectMapper.writeValueAsString(sampleNoteData);
        Mockito.when(service.addNote(Mockito.any(Note.class))).thenReturn(sampleNoteData);

        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/api/notes")
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .content(jsonInput)
                .contentType(MediaType.APPLICATION_JSON_VALUE);

        MvcResult result = mvc.perform(requestBuilder).andReturn();

        String responseBody = result.getResponse().getContentAsString();
        int responseStatus = result.getResponse().getStatus();

        assertEquals(201, responseStatus);
        assertEquals(objectMapper.writeValueAsString(sampleNoteData), responseBody);
    }


}
