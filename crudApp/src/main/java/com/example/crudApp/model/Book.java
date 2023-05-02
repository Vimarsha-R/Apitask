package com.example.crudApp.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "Books")
@NoArgsConstructor(force = true)
@AllArgsConstructor
@Setter
@Getter
@ToString
public class Book {
    @Id
    @GeneratedValue
    private long id;

    @NotNull
    @NotBlank(message = "Author is mandatory")
    private String author;

    @NotNull
    @NotBlank(message = "Title is mandatory")
    private String title;

    private double cost;
    private int pages;
}