package com.svalero.bookshelterapi.service.impl;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.dto.BookPatchDTO;
import com.svalero.bookshelterapi.exception.BookNotFoundException;
import com.svalero.bookshelterapi.repository.BookRepository;
import com.svalero.bookshelterapi.repository.PurchaseRepository;
import com.svalero.bookshelterapi.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;

    @Override
    public List<Book> findAllBooks() {
        return bookRepository.findAll();
    }

    @Override
    public Book findBook(long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }

    @Override
    public List<Book> findByCategory(String categoryName) {
        List<Book> books = bookRepository.findByCategory(categoryName);
        return books;
    }

    @Override
    public List<Book> findByNameAndAuthorAndCategory(String name, String author, String category) {
        List<Book> books = bookRepository.findByNameAndAuthorAndCategory(name, author, category);
        return books;
    }

    @Override
    public List<Book> findByPrice(float price) {
        return bookRepository.findByPrice(price);
    }

    @Override
    public List<Book> findByPriceAndCategory(float price, String category) {
        return bookRepository.findByPriceAndCategory(price, category);
    }

    @Override
    public Book addBook(Book book) {
        return bookRepository.save(book);
    }

    @Override
    public void deleteBookById(long id) throws BookNotFoundException{
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @Override
    public Book modifyBook(long bookId, Book book) throws BookNotFoundException{
        Book newBook = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        newBook.setName(book.getName());
        newBook.setAuthor(book.getAuthor());
        newBook.setCategory(book.getCategory());
        newBook.setPrice(book.getPrice());

        return bookRepository.save(newBook);
    }

    @Override
    public void patchBook(long bookId, BookPatchDTO bookPatchDTO) throws BookNotFoundException {
        Book newBook = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        if (bookPatchDTO.getField().equals("price")){
            newBook.setPrice(Float.parseFloat(bookPatchDTO.getValue()));
        }

        bookRepository.save(newBook);
    }

    @Override
    public List<String> allCategories() {
        List<String> strings = bookRepository.allCategories();
        return strings;
    }

}
