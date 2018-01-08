package com.iqmsoft.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import lombok.Data;

//@Data
@Entity
public class Note {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    private String title;
    private String content;
    private String date;

    public Note( int id, String title, String content, String date ) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Note( String title, String content, String date ) {
        this.title = title;
        this.content = content;
        this.date = date;
    }

    public Note() {
    }

    public int getId() {
        return id;
    }

    public void setId( int id ) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle( String title ) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent( String content ) {
        this.content = content;
    }

    public String getDate() {
        return date;
    }

    public void setDate( String date ) {
        this.date = date;
    }
}
