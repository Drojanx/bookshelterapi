package com.svalero.bookshelterapi.repository;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.PurchaseOutDTO;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PurchaseRepository extends CrudRepository<Purchase, Long> {

    List<Purchase> findAll();
    List<Purchase> findByUser(User user);
    Purchase findByUserAndBook(User user, Book book);
}
