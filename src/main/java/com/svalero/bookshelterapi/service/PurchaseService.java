package com.svalero.bookshelterapi.service;



import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.User;

import java.util.List;

public interface PurchaseService {

    boolean addPurchase(Book book, User user);
    List<Purchase> findPurchases(User user);
    boolean findPurchaseByUserAndBook(User user, Book book);
}
