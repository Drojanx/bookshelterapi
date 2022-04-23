package com.svalero.bookshelterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PositiveOrZero;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewInDTO {

    @PositiveOrZero @Max(10)
    private float stars;
    @NotBlank
    private String comment;
    private boolean published;
    @PositiveOrZero
    private long bookId;
}
