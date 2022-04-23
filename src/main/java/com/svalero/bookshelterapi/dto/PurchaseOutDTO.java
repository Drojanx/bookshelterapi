package com.svalero.bookshelterapi.dto;

import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PurchaseOutDTO {

    private long id;
    private Book book;
    private User user;

    public PurchaseOutDTO clone (PurchaseOutDTO purchaseOutDTO){
        this.setId(purchaseOutDTO.getId());
        this.setBook(purchaseOutDTO.getBook());
        this.setUser(purchaseOutDTO.getUser());
        return this;
    }
}
