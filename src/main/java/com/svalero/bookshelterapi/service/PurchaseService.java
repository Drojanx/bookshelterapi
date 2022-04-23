package com.svalero.bookshelterapi.service;



import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.PatchPurchase;
import com.svalero.bookshelterapi.dto.PurchaseInDTO;
import com.svalero.bookshelterapi.dto.PurchaseOutDTO;
import com.svalero.bookshelterapi.exception.BookAlreadyBoughtException;
import com.svalero.bookshelterapi.exception.BookNotFoundException;
import com.svalero.bookshelterapi.exception.PurchaseNotFoundException;
import com.svalero.bookshelterapi.exception.UserNotFoundException;

import java.util.List;

public interface PurchaseService {

    PurchaseOutDTO addPurchase(long bookId, long userId) throws BookNotFoundException, UserNotFoundException, BookAlreadyBoughtException;
    List<PurchaseOutDTO> findPurchases(User user);
    boolean findPurchaseByUserAndBook(User user, Book book);
    PurchaseOutDTO modifyPurchase(long purchaseId, PurchaseInDTO purchaseInDTO) throws PurchaseNotFoundException, UserNotFoundException, BookNotFoundException;
    void patchPurchase(long purchaseId, PatchPurchase patchPurchase) throws PurchaseNotFoundException, BookNotFoundException;
    void deletePurchase(long purchaseId) throws PurchaseNotFoundException;
}
