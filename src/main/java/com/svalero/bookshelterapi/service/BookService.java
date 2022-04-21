package com.svalero.bookshelterapi.service;



import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.dto.BookPatchDTO;
import com.svalero.bookshelterapi.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    List<Book> findAllBooks();
    Book findBook(long id) throws BookNotFoundException;
    List<Book> findByCategory(String categoryName);
    List<Book> findByNameAndAuthorAndCategory(String name, String author, String category);
    List<Book> findByPrice(float price);
    List<Book> findByPriceAndCategory(float price, String category);

    Book addBook(Book book);
    void deleteBookById(long id) throws BookNotFoundException;

    Book modifyBook(long bookId, Book book)  throws BookNotFoundException;
    void patchBook(long bookId, BookPatchDTO bookPatchDTO) throws BookNotFoundException;

    List<String> allCategories();


}
