package com.alurachallenge.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.persistence.*;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private Integer birthdayYear;
    private Integer deathYear;
    @OneToOne
    private Book book;

    public  Author(){}
    public Author(AuthorData a) {
        this.name = a.name();
        if(a.birthdayYear() > 0){
            this.birthdayYear = a.birthdayYear();
        }else{
            this.birthdayYear = 0;
        }
        if (a.deathYear() > 0){
            this.deathYear = a.deathYear();
        }else {
            this.deathYear = 0;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBirthdayYear() {
        return birthdayYear;
    }

    public void setBirthdayYear(Integer birthdayYear) {
        this.birthdayYear = birthdayYear;
    }

    public Integer getDeathYear() {
        return deathYear;
    }

    public void setDeathYear(Integer deathYear) {
        this.deathYear = deathYear;
    }

    public Book getBook() {
        return book;
    }

    public void setBook(Book book) {
        this.book = book;
    }


    @Override
    public String toString() {
        return "----------AUTOR-----------\n" +
                "NOMBRE: " + name + "\n" +
                "NACIMIENTO: " + birthdayYear +"\n" +
                "MUERTE: " + deathYear + "\n" +
                "LIBRO: " + book.getTitulo() + "\n" +
                "--------------------------\n";
    }
}
