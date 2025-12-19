package com.example.demo.model;

import java.time.LocalDate;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
@Schema(description = "Un livre dans la bibliothèque")
public class Book {

    @Id
    @SequenceGenerator(name = "book_seq", sequenceName = "book_seq", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "book_seq")
    @Column(name = "id", updatable = false, nullable = false)
    @Schema(description = "ID unique du livre", example = "1")
    private Long id;

    @Schema(description = "Titre du livre", example = "Le Petit Prince")
    private String title;

    @Schema(description = "Auteur du livre", example = "Antoine de Saint-Exupéry")
    private String author;

    @Schema(description = "Numéro ISBN", example = "978-2070612758")
    private String isbn;

    @Schema(description = "Date de publication")
    private LocalDate publishedDate;

    @Schema(description = "Nombre de pages")
    private Integer pages;

    @Schema(description = "Catégorie/genre du livre")
    private String category;

    @Schema(description = "Disponible à l'emprunt")
    private Boolean available;

    @Schema(description = "Prix en euros", example = "12.99")
    private Double price;

    // Constructeurs
    public Book() {
    }

    public Book(String title, String author, String isbn, Double price) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.price = price;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getIsbn() {
        return isbn;
    }

    public void setIsbn(String isbn) {
        this.isbn = isbn;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public Integer getPages() {
        return pages;
    }

    public void setPages(Integer pages) {
        this.pages = pages;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}