package com.svalero.bookshelterapi.controller;

import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.dto.BookPatchDTO;
import com.svalero.bookshelterapi.dto.ErrorResponse;
import com.svalero.bookshelterapi.exception.BookNotFoundException;
import com.svalero.bookshelterapi.service.BookService;
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


    // Añadir libros
    @PostMapping(value = "/books")
    public ResponseEntity<Book> addBook(@Valid @RequestBody Book book){
        Book newBook = bookService.addBook(book);
        return new ResponseEntity<>(newBook, HttpStatus.CREATED);
    }

    // Eliminar libros
    @DeleteMapping(value = "/book/{bookId}")
    public ResponseEntity<Void> deleteBook(@PathVariable long bookId) throws BookNotFoundException{
        bookService.deleteBookById(bookId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Modificar libro (modificación "completa", por eso usamos PUT)
    @PutMapping(value = "/book/{bookId}")
    public ResponseEntity<Book> modifyBook(@PathVariable long bookId, @RequestBody Book book) throws BookNotFoundException {
        Book newBook = bookService.modifyBook(bookId, book);
        return new ResponseEntity<>(newBook, HttpStatus.OK);
    }

    // Modificar precio libro (modificación "parcial", por eso PATCH)
    @PatchMapping(value = "/book/{bookId}")
    public ResponseEntity<Void> patchBook(@PathVariable long bookId, @RequestBody BookPatchDTO bookPatchDTO) throws BookNotFoundException {
        bookService.patchBook(bookId, bookPatchDTO);
        return ResponseEntity.noContent().build();
    }

    // Ver libros
    @GetMapping(value = "/books")
    public ResponseEntity<List<Book>> getBooks(@RequestParam(defaultValue = "0") float price,
                               @RequestParam(defaultValue = "") String category){
        List<Book> books = null;
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

    // Ver libro
    @GetMapping(value = "/book/{bookId}")
    public ResponseEntity<Book> getBook(@PathVariable long bookId) throws BookNotFoundException {
        Book book = bookService.findBook(bookId);
        return ResponseEntity.ok(book);
    }

    @ExceptionHandler(BookNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleException(BookNotFoundException pnfe) {
        ErrorResponse errorResponse = ErrorResponse.generalError(101, pnfe.getMessage());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    //TODO Controlar exceptions imprevistas
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
