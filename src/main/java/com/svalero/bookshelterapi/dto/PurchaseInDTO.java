package com.svalero.bookshelterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.PositiveOrZero;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseInDTO {

    @PositiveOrZero
    private long bookId;
    private boolean free;
    @PositiveOrZero
    private long userId;

}
