package com.svalero.bookshelterapi.controller;


import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.ErrorResponse;
import com.svalero.bookshelterapi.dto.PatchUser;
import com.svalero.bookshelterapi.dto.UserInDTO;
import com.svalero.bookshelterapi.dto.UserOutDTO;
import com.svalero.bookshelterapi.exception.PurchaseNotFoundException;
import com.svalero.bookshelterapi.exception.ReviewNotFoundException;
import com.svalero.bookshelterapi.exception.UserModificationException;
import com.svalero.bookshelterapi.exception.UserNotFoundException;
import com.svalero.bookshelterapi.service.PurchaseService;
import com.svalero.bookshelterapi.service.ReviewService;
import com.svalero.bookshelterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private PurchaseService purchaseService;
    @Autowired
    private ReviewService reviewService;

    // Registrar usuario
    @PostMapping(value = "/users")
    public ResponseEntity<UserOutDTO> addUser(@Valid @RequestBody UserInDTO userInDTO){
        UserOutDTO userOutDTO = userService.addUser(userInDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(userOutDTO);
    }

    // Ver usuarios
    @GetMapping(value = "/users")
    public ResponseEntity<List<User>> getUsers() {
        List<User> users = null;
        users = userService.findAllUsers();
        return ResponseEntity.ok().body(users);
    }

    @GetMapping(value = "/user/{userId}")
    public ResponseEntity<UserOutDTO> getUsers(@PathVariable long userId) throws UserNotFoundException {
        UserOutDTO userOutDTO = userService.findUserDTO(userId);
        return ResponseEntity.ok().body(userOutDTO);
    }

    // Borrar usuario
    @DeleteMapping(value = "/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable long userId) throws UserNotFoundException, ReviewNotFoundException, PurchaseNotFoundException {
        User user = userService.findUser(userId);
        List<Purchase> purchaseList = user.getPurchases();
        List<Review> reviewList = user.getReviews();
        for (Purchase purchase : purchaseList){
            purchaseService.deletePurchase(purchase.getId());
        }
        for (Review review : reviewList){
            reviewService.deleteReview(review.getId());
        }
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Editar usuario
    @PutMapping(value = "/user/{userId}")
    public ResponseEntity<UserOutDTO> modifyUser(@PathVariable long userId, @Valid @RequestBody UserInDTO userInDTO) throws UserNotFoundException, UserModificationException {
        UserOutDTO userOutDTO = userService.modifyUser(userId, userInDTO);
        return new ResponseEntity<>(userOutDTO, HttpStatus.OK);
    }

    @PatchMapping(value = "/user/{userId}")
    public ResponseEntity<Void> patchUser(@PathVariable long userId, @Valid @RequestBody PatchUser patchUser) throws UserNotFoundException, UserModificationException {
        userService.patchUser(userId, patchUser);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(PurchaseNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(PurchaseNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(103, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ReviewNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(105, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserModificationException.class)
    public ResponseEntity<ErrorResponse> handleException(UserModificationException ume) {
        ErrorResponse errorResponse = ErrorResponse.generalError(107, ume.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(102, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
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
