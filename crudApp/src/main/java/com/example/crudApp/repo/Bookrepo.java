package com.example.crudApp.repo;

import com.example.crudApp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Bookrepo extends JpaRepository<Book,Long> {

    boolean existsByAuthorAndTitle(String author, String title);


    boolean existsBytitle(String title);

    boolean existsByauthor(String author);
}
