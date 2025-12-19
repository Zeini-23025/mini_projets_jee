package com.example.demo.controller;

import com.example.demo.model.Book;
import com.example.demo.repository.BookRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
@Tag(name = "Books", description = "API de gestion des livres")
public class BookController {

    @Autowired
    private BookRepository bookRepository;

    // GET /api/books - Récupérer tous les livres
    @Operation(summary = "Récupérer tous les livres", 
               description = "Retourne la liste complète des livres")
    @ApiResponse(responseCode = "200", description = "Liste récupérée avec succès")
    @GetMapping
    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    // GET /api/books/{id} - Récupérer un livre par ID
    @Operation(summary = "Récupérer un livre par ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre trouvé"),
        @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(
            @Parameter(description = "ID du livre", example = "1")
            @PathVariable Long id) {
        Optional<Book> book = bookRepository.findById(id);
        return book.map(ResponseEntity::ok)
                   .orElse(ResponseEntity.notFound().build());
    }

    // POST /api/books - Créer un nouveau livre
    @Operation(summary = "Créer un nouveau livre")
    @ApiResponse(responseCode = "201", description = "Livre créé avec succès")
    @PostMapping
    public ResponseEntity<Book> createBook(@RequestBody Book book) {
        Book savedBook = bookRepository.save(book);
        return new ResponseEntity<>(savedBook, HttpStatus.CREATED);
    }

    // PUT /api/books/{id} - Mettre à jour un livre
    @Operation(summary = "Mettre à jour un livre existant")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Livre mis à jour"),
        @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @Parameter(description = "ID du livre") @PathVariable Long id,
            @RequestBody Book bookDetails) {
        Optional<Book> bookOpt = bookRepository.findById(id);
        
        if (bookOpt.isPresent()) {
            Book book = bookOpt.get();
            book.setTitle(bookDetails.getTitle());
            book.setAuthor(bookDetails.getAuthor());
            book.setIsbn(bookDetails.getIsbn());
            book.setPrice(bookDetails.getPrice());
            Book updatedBook = bookRepository.save(book);
            return ResponseEntity.ok(updatedBook);
        }
        
        return ResponseEntity.notFound().build();
    }

    // DELETE /api/books/{id} - Supprimer un livre
    @Operation(summary = "Supprimer un livre")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Livre supprimé"),
        @ApiResponse(responseCode = "404", description = "Livre non trouvé")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(
            @Parameter(description = "ID du livre") @PathVariable Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    // GET /api/books/search?author=nom - Rechercher par auteur
    @Operation(summary = "Rechercher des livres par auteur")
    @GetMapping("/search")
    public List<Book> searchByAuthor(
            @Parameter(description = "Nom de l'auteur", example = "Victor Hugo")
            @RequestParam String author) {
        return bookRepository.findByAuthorContainingIgnoreCase(author);
    }
}