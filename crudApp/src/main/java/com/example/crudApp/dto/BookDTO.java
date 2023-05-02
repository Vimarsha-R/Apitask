package com.example.crudApp.dto;

public class BookDTO {
    private final String author;
    //private final String title;

    public BookDTO(String author) {  //String title
        this.author = author;
        //this.title = title;
    }

    public String getAuthor() {

        return author;
    }

//    public String getTitle() {
//        return title;
//    }
}
