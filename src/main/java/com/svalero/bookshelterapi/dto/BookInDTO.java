package com.svalero.bookshelterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInDTO {

    private String name;
    private String author;
    private String category;
    private float price;
    private LocalDate creationDate;

}
