package com.svalero.bookshelterapi.controller;

import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.*;
import com.svalero.bookshelterapi.exception.*;
import com.svalero.bookshelterapi.service.ReviewService;
import com.svalero.bookshelterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Lookup;
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
public class ReviewController {

    @Autowired
    private ReviewService reviewService;
    @Autowired
    private UserService userService;


    // Hacer review
    @PostMapping(value = "/user/{userId}/reviews")
    public ResponseEntity<ReviewOutDTO> addReview(@PathVariable long userId, @Valid @RequestBody ReviewInDTO reviewInDTO) throws UserNotFoundException, BookNotFoundException, BookNotBoughtException, BookAlreadyReviewedException{
        ReviewOutDTO reviewOutDTO = reviewService.addReview(reviewInDTO, userId);
        if (reviewOutDTO.getId() == 0){
            throw new BookNotBoughtException();
        }
        return new ResponseEntity<>(reviewOutDTO, HttpStatus.CREATED);
    }

    // Ver reviews
    @GetMapping(value = "/user/{userId}/reviews")
    public ResponseEntity<List<ReviewOutDTO>> getReviews(@PathVariable long userId) throws UserNotFoundException, ReviewNotFoundException {
        User user = userService.findUser(userId);
        List<ReviewOutDTO> reviewOutDTOList = reviewService.findReviews(user);
        return new ResponseEntity<>(reviewOutDTOList, HttpStatus.OK);
    }

    @GetMapping(value = "/review/{reviewId}")
    public ResponseEntity<ReviewOutDTO> getReview(@PathVariable long reviewId) throws ReviewNotFoundException {
        ReviewOutDTO reviewOutDTO = reviewService.findById(reviewId);
        return new ResponseEntity<>(reviewOutDTO, HttpStatus.OK);
    }

    // Modificar review
    @PutMapping(value = "/review/{reviewId}")
    public ResponseEntity<ReviewOutDTO> modifyReview(@PathVariable long reviewId, @Valid @RequestBody ReviewInDTO reviewInDTO) throws UserNotFoundException, BookNotFoundException, ReviewNotFoundException {
        ReviewOutDTO reviewOutDTO = reviewService.modifyReview(reviewId, reviewInDTO);
        return new ResponseEntity<>(reviewOutDTO, HttpStatus.OK);
    }

    @PatchMapping(value = "/review/{reviewId}")
    public ResponseEntity<Void> patchReview(@PathVariable long reviewId, @RequestBody PatchReview patchReview) throws ReviewNotFoundException {
        reviewService.patchReview(reviewId, patchReview);
        return ResponseEntity.noContent().build();
    }

    // Eliminar review
    @DeleteMapping(value = "/review/{reviewId}")
    public ResponseEntity<Void> deleteReview(@PathVariable long reviewId) throws ReviewNotFoundException {
        reviewService.deleteReview(reviewId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ReviewNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(ReviewNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(105, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(102, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(BookNotBoughtException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotBoughtException bnbe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(106, bnbe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(BookAlreadyReviewedException.class)
    public ResponseEntity<ErrorResponse> handleException(BookAlreadyReviewedException bnbe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, bnbe.getMessage());
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
