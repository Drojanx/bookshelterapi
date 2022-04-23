package com.svalero.bookshelterapi.service.impl;

import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.PurchaseInDTO;
import com.svalero.bookshelterapi.dto.PurchaseOutDTO;
import com.svalero.bookshelterapi.repository.PurchaseRepository;
import com.svalero.bookshelterapi.service.PurchaseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
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

    @Override
    public PurchaseOutDTO addPurchase(Book book, User user) {
        Purchase purchase = new Purchase();
        purchase.setCreationDate(LocalDate.now());
        purchase.setBook(book);
        purchase.setUser(user);
        user.addPurchaseToUser(purchase);
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
    public boolean findPurchaseByUserAndBook(User user, Book book) {
        return purchaseRepository.findByUserAndBook(user, book)!=null;
    }


}
