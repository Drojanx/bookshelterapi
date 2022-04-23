package com.svalero.bookshelterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PurchasePatchDTO {

    private String field;
    private String value;
}
