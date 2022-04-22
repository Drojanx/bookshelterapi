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
    private LocalDate date;
    private Book book;
    private User user;
}
