package com.alurachallenge.literalura.repository;

import com.alurachallenge.literalura.models.Author;
import com.alurachallenge.literalura.models.AuthorData;
import com.alurachallenge.literalura.models.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface IBookRepository extends JpaRepository<Book, Long> {

    @Query("SELECT a FROM Author a")
    List<Author> findByAuthor();

    @Query("SELECT a FROM Author a WHERE a.deathYear > :age")
    List<Author> getAliveAuthors(int age);
}
