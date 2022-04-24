package com.svalero.bookshelterapi.controller;

import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.*;
import com.svalero.bookshelterapi.exception.BookAlreadyBoughtException;
import com.svalero.bookshelterapi.exception.BookNotFoundException;
import com.svalero.bookshelterapi.exception.PurchaseNotFoundException;
import com.svalero.bookshelterapi.exception.UserNotFoundException;
import com.svalero.bookshelterapi.service.BookService;
import com.svalero.bookshelterapi.service.PurchaseService;
import com.svalero.bookshelterapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class PurchaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ModelMapper modelMapper;


    // Hacer compra
    @PostMapping(value = "/user/{userId}/purchases")
    public ResponseEntity<PurchaseOutDTO> addPurchase(@PathVariable long userId, @Valid @RequestBody PurchaseInDTO purchaseInDTO) throws UserNotFoundException, BookNotFoundException, BookAlreadyBoughtException {
        PurchaseOutDTO purchaseOutDTO = purchaseService.addPurchase(purchaseInDTO.getBookId(), userId, purchaseInDTO.isFree());
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
    @PutMapping(value = "/purchase/{purchaseId}")
    public ResponseEntity<PurchaseOutDTO> modifyPurchase(@PathVariable long purchaseId, @Valid @RequestBody PurchaseInDTO purchaseInDTO) throws UserNotFoundException, BookNotFoundException, PurchaseNotFoundException {
        PurchaseOutDTO purchaseOutDTO = purchaseService.modifyPurchase(purchaseId, purchaseInDTO);
        return new ResponseEntity<>(purchaseOutDTO, HttpStatus.OK);
    }

    @PatchMapping(value = "/purchase/{purchaseId}")
    public ResponseEntity<Void> patchPurchase(@PathVariable long purchaseId, @Valid @RequestBody PatchPurchase patchPurchase) throws PurchaseNotFoundException, BookNotFoundException {
        purchaseService.patchPurchase(purchaseId, patchPurchase);
        return ResponseEntity.noContent().build();
    }
    // Eliminar compra
    @DeleteMapping(value = "/purchase/{purchaseId}")
    public ResponseEntity<Void> deleteBook(@PathVariable long purchaseId) throws PurchaseNotFoundException{
        purchaseService.deletePurchase(purchaseId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(PurchaseNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(102, unfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookAlreadyBoughtException.class)
    public ResponseEntity<ErrorResponse> handleException(BookAlreadyBoughtException babe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(103, babe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleException(MethodArgumentNotValidException manve) {
        Map<String, String> errors = new HashMap<>();
        manve.getBindingResult().getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return ResponseEntity.badRequest().body(ErrorResponse.validationError(errors));
    }
}
