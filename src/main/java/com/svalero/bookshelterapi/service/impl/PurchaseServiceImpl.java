package com.svalero.bookshelterapi.service.impl;

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
import com.svalero.bookshelterapi.repository.PurchaseRepository;
import com.svalero.bookshelterapi.service.BookService;
import com.svalero.bookshelterapi.service.PurchaseService;
import com.svalero.bookshelterapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class PurchaseServiceImpl implements PurchaseService {

    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private BookService bookService;
    @Autowired
    private UserService userService;

    @Override
    public PurchaseOutDTO addPurchase(long bookId, long userId, boolean isFree) throws BookNotFoundException, UserNotFoundException, BookAlreadyBoughtException {
        Purchase purchase = new Purchase();
        Book book = bookService.findBook(bookId);
        User user = userService.findUser(userId);
        purchase.setCreationDate(LocalDate.now());
        purchase.setBook(book);
        purchase.setUser(user);
        if(isFree){
            purchase.setFree(true);
            purchase.setPrice(0);
        } else{
            purchase.setFree(false);
            purchase.setPrice(book.getPrice());
        }
        user.addPurchaseToUser(purchase);
        if (bookService.isBought(book, user)){
            throw new BookAlreadyBoughtException();
        }
        Purchase newPurchase = purchaseRepository.save(purchase);

        PurchaseOutDTO purchaseOutDTO = new PurchaseOutDTO();
        modelMapper.map(newPurchase, purchaseOutDTO);
        return purchaseOutDTO;
    }

    @Override
    public List<PurchaseOutDTO> findPurchases(User user) {
        List<Purchase> purchaseList = purchaseRepository.findByUser(user);

        List<PurchaseOutDTO> purchaseOutDTOList = new ArrayList<PurchaseOutDTO>();
        PurchaseOutDTO purchaseOutDTO = new PurchaseOutDTO();
        for (Purchase purchase : purchaseList) {
            modelMapper.map(purchase, purchaseOutDTO);
            PurchaseOutDTO purchaseOutDTOcopy = new PurchaseOutDTO();
            purchaseOutDTOcopy.clone(purchaseOutDTO);
            purchaseOutDTOList.add(purchaseOutDTOcopy);
        }
        return purchaseOutDTOList;
    }

    @Override
    public PurchaseOutDTO modifyPurchase(long purchaseId, PurchaseInDTO purchaseInDTO) throws PurchaseNotFoundException, UserNotFoundException, BookNotFoundException {
        Purchase newPurchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(PurchaseNotFoundException::new);
        Book book = bookService.findBook(purchaseInDTO.getBookId());
        newPurchase.setBook(book);
        User user = userService.findUser(newPurchase.getUser().getId());
        newPurchase.setUser(user);
        purchaseRepository.save(newPurchase);

        PurchaseOutDTO newPurchaseOutDTO = new PurchaseOutDTO();
        modelMapper.map(newPurchase, newPurchaseOutDTO);
        return newPurchaseOutDTO;
    }

    @Override
    public void patchPurchase(long purchaseId, PatchPurchase patchPurchase) throws PurchaseNotFoundException, BookNotFoundException {
        Purchase newPurchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(PurchaseNotFoundException::new);
        if (patchPurchase.getField().equals("free")){
            boolean free = Boolean.parseBoolean(patchPurchase.getValue());
            newPurchase.setFree(free);
            if(free){
                newPurchase.setPrice(0);
            } else {
                newPurchase.setPrice(newPurchase.getBook().getPrice());
            }
        }

        purchaseRepository.save(newPurchase);
    }

    @Override
    public void deletePurchase(long purchaseId) throws PurchaseNotFoundException{
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(PurchaseNotFoundException::new);
        purchaseRepository.delete(purchase);
    }

    @Override
    public PurchaseOutDTO findByIdDTO(long purchaseId) throws PurchaseNotFoundException{
        Purchase purchase = purchaseRepository.findById(purchaseId)
                .orElseThrow(PurchaseNotFoundException::new);
        PurchaseOutDTO purchaseOutDTO = new PurchaseOutDTO();
        modelMapper.map(purchase, purchaseOutDTO);
        return purchaseOutDTO;
    }

    @Override
    public boolean findPurchaseByUserAndBook(User user, Book book) {
        return purchaseRepository.findByUserAndBook(user, book)!=null;
    }


}
