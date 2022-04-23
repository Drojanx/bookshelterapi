package com.svalero.bookshelterapi.service;



import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.PurchaseInDTO;
import com.svalero.bookshelterapi.dto.PurchaseOutDTO;

import java.util.List;

public interface PurchaseService {

    PurchaseOutDTO addPurchase(Book book, User user);
    List<PurchaseOutDTO> findPurchases(User user);
    boolean findPurchaseByUserAndBook(User user, Book book);
}
