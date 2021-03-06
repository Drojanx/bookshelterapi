package com.svalero.bookshelterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookInDTO {

    @NotBlank
    private String name;
    @NotBlank
    private String author;
    @NotBlank
    private String category;
    @PositiveOrZero
    private float price;

}
