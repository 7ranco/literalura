package com.alurachallenge.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private int nit;
    @Column(unique = true)
    private String titulo;
    private List<String> subjects;
    private String  languages;
    private Double downloads;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Author authors;


    public Book() {
    }

    public Book(BookData bookData) {
        this.nit = bookData.id();
        this.titulo = bookData.title();
        this.subjects = bookData.subjects();
        this.languages = bookData.languages().get(0);
        this.downloads = bookData.downloads();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getNit() {
        return nit;
    }

    public void setNit(int nit) {
        this.nit = nit;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public List<String> getSubjects() {
        return subjects;
    }

    public void setSubjects(List<String> subjects) {
        this.subjects = subjects;
    }

    public String getLanguages() {
        return languages;
    }

    public void setLanguages(String languages) {
        this.languages = languages;
    }

    public Double getDownloads() {
        return downloads;
    }

    public void setDownloads(Double downloads) {
        this.downloads = downloads;
    }



    public Author getAuthors() {
        return authors;
    }

    public void setAuthors(Author authors) {
        authors.setBook(this);
        this.authors = authors;
    }

    @Override
    public String toString() {
        return "--------LIBRO---------\n" +
                "NIT: " + nit +"\n" +
                "TITULO: '" + titulo + "\n" +
                "SUBJECTS: " + subjects +"\n" +
                "LENGUAJE: " + languages +"\n" +
                "DESCARGAS: " + downloads + "\n" +
                "----------------------\n";
    }
}