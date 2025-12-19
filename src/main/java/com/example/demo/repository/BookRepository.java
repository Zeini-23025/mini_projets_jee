package com.example.demo.repository;

import com.example.demo.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {
    // Recherche par auteur
    List<Book> findByAuthorContainingIgnoreCase(String author);
    
    // Recherche par catégorie
    List<Book> findByCategory(String category);
}
