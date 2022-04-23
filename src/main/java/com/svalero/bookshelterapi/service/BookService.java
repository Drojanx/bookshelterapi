package com.svalero.bookshelterapi.service;



import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.BookInDTO;
import com.svalero.bookshelterapi.dto.BookOutDTO;
import com.svalero.bookshelterapi.dto.PatchBook;
import com.svalero.bookshelterapi.exception.BookNotFoundException;

import java.util.List;

public interface BookService {

    List<BookOutDTO> findAllBooks();
    BookOutDTO findBookDTO(long id) throws BookNotFoundException;
    Book findBook(long id) throws BookNotFoundException;
    List<BookOutDTO> findByCategory(String categoryName);
    List<Book> findByNameAndAuthorAndCategory(String name, String author, String category);
    List<BookOutDTO> findByPrice(float price);
    List<BookOutDTO> findByPriceAndCategory(float price, String category);

    BookOutDTO addBook(BookInDTO bookInDTO);
    void deleteBookById(long id) throws BookNotFoundException;

    BookOutDTO modifyBook(long bookId, BookInDTO bookInDTO)  throws BookNotFoundException;
    void patchBook(long bookId, PatchBook patchBook) throws BookNotFoundException;

    List<String> allCategories();

    boolean isBought(Book book, User user);
    boolean isReviewed(Book book, User user);
}
