package com.alurachallenge.literalura.models;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AuthorData (@JsonAlias("name") String name,
                          @JsonAlias("birth_year") Integer birthdayYear,
                          @JsonAlias("death_year") Integer deathYear){
}
