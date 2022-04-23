package com.svalero.bookshelterapi.controller;

import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.ErrorResponse;
import com.svalero.bookshelterapi.dto.PurchaseInDTO;
import com.svalero.bookshelterapi.dto.PurchaseOutDTO;
import com.svalero.bookshelterapi.exception.BookNotFoundException;
import com.svalero.bookshelterapi.exception.UserNotFoundException;
import com.svalero.bookshelterapi.service.BookService;
import com.svalero.bookshelterapi.service.PurchaseService;
import com.svalero.bookshelterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class PurchaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private PurchaseService purchaseService;

    // Hacer compra
    @PostMapping(value = "/user/{userId}/purchases")
    public ResponseEntity<PurchaseOutDTO> addPurchase(@PathVariable long userId, @RequestBody PurchaseInDTO purchaseInDTO) throws UserNotFoundException, BookNotFoundException {
        User user = userService.findUser(userId);
        Book book = bookService.findBook(purchaseInDTO.getBookId());
        PurchaseOutDTO purchaseOutDTO = purchaseService.addPurchase(book, user);
        return new ResponseEntity<>(purchaseOutDTO, HttpStatus.CREATED);
    }

    //Ver pedidos de un usuario
    @GetMapping(value = "/user/{userId}/purchases")
    public ResponseEntity<List<PurchaseOutDTO>> getPurchases(@PathVariable long userId) throws UserNotFoundException{
        User user = userService.findUser(userId);
        List<PurchaseOutDTO> purchaseOutDTOList = purchaseService.findPurchases(user);
        return new ResponseEntity<>(purchaseOutDTOList, HttpStatus.OK);
    }

    // Modificar compra

    // Eliminar compra

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, unfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }
}
