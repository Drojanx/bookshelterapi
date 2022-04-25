package com.svalero.bookshelterapi.controller;

import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.dto.BookInDTO;
import com.svalero.bookshelterapi.dto.BookOutDTO;
import com.svalero.bookshelterapi.dto.PatchBook;
import com.svalero.bookshelterapi.dto.ErrorResponse;
import com.svalero.bookshelterapi.exception.BookNotFoundException;
import com.svalero.bookshelterapi.exception.PurchaseNotFoundException;
import com.svalero.bookshelterapi.exception.ReviewNotFoundException;
import com.svalero.bookshelterapi.exception.UserNotFoundException;
import com.svalero.bookshelterapi.service.BookService;
import com.svalero.bookshelterapi.service.PurchaseService;
import com.svalero.bookshelterapi.service.ReviewService;
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
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private ReviewService reviewService;
    @Autowired
    private PurchaseService purchaseService;

    // Ver libros
    @GetMapping(value = "/books")
    public ResponseEntity<List<BookOutDTO>> getBooks(@RequestParam(defaultValue = "0") float price,
                                                     @RequestParam(defaultValue = "") String category){
        List<BookOutDTO> books = null;
        if ((price != 0)) {
            if (category.equals(""))
                books = bookService.findByPrice(price);
            else
                books = bookService.findByPriceAndCategory(price, category);
        } else if (!category.equals("")){
            if (price == 0)
                books = bookService.findByCategory(category);
            else
                books =  bookService.findByPriceAndCategory(price, category);
        }
        if(books != null)
            return ResponseEntity.ok().body(books);
        books = bookService.findAllBooks();
        return ResponseEntity.ok().body(books);
    }

    // Añadir libros
    @PostMapping(value = "/books")
    public ResponseEntity<BookOutDTO> addBook(@Valid @RequestBody BookInDTO bookInDTO){
        BookOutDTO bookOutDTO = bookService.addBook(bookInDTO);
        return new ResponseEntity<>(bookOutDTO, HttpStatus.CREATED);
    }

    // Ver libro
    @GetMapping(value = "/book/{bookId}")
    public ResponseEntity<BookOutDTO> getBook(@PathVariable long bookId) throws BookNotFoundException {
        BookOutDTO bookOutDTO = bookService.findBookDTO(bookId);
        return ResponseEntity.ok(bookOutDTO);
    }

    // Eliminar libros
    @DeleteMapping(value = "/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable long bookId) throws BookNotFoundException, PurchaseNotFoundException, ReviewNotFoundException {
        Book book = bookService.findBook(bookId);
        List<Purchase> purchaseList = book.getPurchases();
        List<Review> reviewList = book.getReviews();
        for (Purchase purchase : purchaseList){
            purchaseService.deletePurchase(purchase.getId());
        }
        for (Review review : reviewList){
            reviewService.deleteReview(review.getId());
        }
        bookService.deleteBookById(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Modificar libro (modificación "completa", por eso usamos PUT)
    @PutMapping(value = "/book/{bookId}")
    public ResponseEntity<BookOutDTO> modifyBook(@PathVariable long bookId, @Valid @RequestBody BookInDTO bookInDTO) throws BookNotFoundException {
        BookOutDTO bookOutDTO = bookService.modifyBook(bookId, bookInDTO);
        return new ResponseEntity<>(bookOutDTO, HttpStatus.OK);
    }

    // Modificar precio libro (modificación "parcial", por eso PATCH)
    @PatchMapping(value = "/book/{bookId}")
    public ResponseEntity<Void> patchBook(@PathVariable long bookId, @RequestBody PatchBook patchBook) throws BookNotFoundException {
        bookService.patchBook(bookId, patchBook);
        return ResponseEntity.noContent().build();
    }


    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(UserNotFoundException unfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(102, unfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleException(Exception e){
        ErrorResponse errorResponse = ErrorResponse.generalError(999, "Error imprevisto");
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
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
