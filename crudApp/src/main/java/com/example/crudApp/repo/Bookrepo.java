package com.example.crudApp.repo;

import com.example.crudApp.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface Bookrepo extends JpaRepository<Book,Long> {
}
