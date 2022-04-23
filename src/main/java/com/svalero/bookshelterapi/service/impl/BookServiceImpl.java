package com.svalero.bookshelterapi.service.impl;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.BookInDTO;
import com.svalero.bookshelterapi.dto.BookOutDTO;
import com.svalero.bookshelterapi.dto.PatchBook;
import com.svalero.bookshelterapi.dto.PurchaseOutDTO;
import com.svalero.bookshelterapi.exception.BookNotFoundException;
import com.svalero.bookshelterapi.repository.BookRepository;
import com.svalero.bookshelterapi.repository.PurchaseRepository;
import com.svalero.bookshelterapi.service.BookService;
import com.svalero.bookshelterapi.service.ReviewService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class BookServiceImpl implements BookService {

    @Autowired
    private BookRepository bookRepository;
    @Autowired
    private PurchaseRepository purchaseRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<BookOutDTO> findAllBooks() {
        List<Book> bookList = bookRepository.findAll();

        List<BookOutDTO> bookOutDTOList = new ArrayList<BookOutDTO>();
        mapBookDTOLists(bookList, bookOutDTOList);
        return bookOutDTOList;
    }

    @Override
    public BookOutDTO findBookDTO(long id) throws BookNotFoundException {
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        BookOutDTO bookOutDTO = new BookOutDTO();
        modelMapper.map(book, bookOutDTO);
        return bookOutDTO;
    }

    @Override
    public Book findBook(long id) throws BookNotFoundException {
        return bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
    }



    @Override
    public List<BookOutDTO> findByCategory(String categoryName) {
        List<Book> bookList = bookRepository.findByCategory(categoryName);

        List<BookOutDTO> bookOutDTOList = new ArrayList<BookOutDTO>();
        mapBookDTOLists(bookList, bookOutDTOList);
        return bookOutDTOList;
    }

    @Override
    public List<Book> findByNameAndAuthorAndCategory(String name, String author, String category) {
        List<Book> books = bookRepository.findByNameAndAuthorAndCategory(name, author, category);
        return books;
    }

    @Override
    public List<BookOutDTO> findByPrice(float price) {
        List<Book> bookList = bookRepository.findByPrice(price);

        List<BookOutDTO> bookOutDTOList = new ArrayList<BookOutDTO>();
        BookOutDTO bookOutDTO = new BookOutDTO();
        for (Book book : bookList) {
            modelMapper.map(book, bookOutDTO);
            BookOutDTO bookOutDTOcopy = new BookOutDTO();
            bookOutDTOcopy.clone(bookOutDTO);
            bookOutDTOList.add(bookOutDTOcopy);
        }
        return bookOutDTOList;
    }

    @Override
    public List<BookOutDTO> findByPriceAndCategory(float price, String category) {
        List<Book> bookList = bookRepository.findByPriceAndCategory(price, category);

        List<BookOutDTO> bookOutDTOList = new ArrayList<BookOutDTO>();
        BookOutDTO bookOutDTO = new BookOutDTO();
        for (Book book : bookList) {
            modelMapper.map(book, bookOutDTO);
            BookOutDTO bookOutDTOcopy = new BookOutDTO();
            bookOutDTOcopy.clone(bookOutDTO);
            bookOutDTOList.add(bookOutDTOcopy);
        }
        return bookOutDTOList;
    }

    @Override
    public BookOutDTO addBook(BookInDTO bookInDTO) {
        Book book = new Book();
        book.setCreationDate(LocalDate.now());
        modelMapper.map(bookInDTO, book);
        Book newBook = bookRepository.save(book);

        BookOutDTO bookOutDTO = new BookOutDTO();
        modelMapper.map(book, bookOutDTO);
        return bookOutDTO;
    }

    @Override
    public void deleteBookById(long id) throws BookNotFoundException{
        Book book = bookRepository.findById(id)
                .orElseThrow(BookNotFoundException::new);
        bookRepository.delete(book);
    }

    @Override
    public BookOutDTO modifyBook(long bookId, BookInDTO bookInDTO) throws BookNotFoundException{
        Book newBook = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        newBook.setName(bookInDTO.getName());
        newBook.setAuthor(bookInDTO.getAuthor());
        newBook.setCategory(bookInDTO.getCategory());
        newBook.setPrice(bookInDTO.getPrice());
        bookRepository.save(newBook);

        BookOutDTO newBookOutDTO = new BookOutDTO();
        modelMapper.map(newBook, newBookOutDTO);

        return newBookOutDTO;
    }

    @Override
    public void patchBook(long bookId, PatchBook patchBook) throws BookNotFoundException {
        Book newBook = bookRepository.findById(bookId)
                .orElseThrow(BookNotFoundException::new);
        if (patchBook.getField().equals("price")){
            newBook.setPrice(Float.parseFloat(patchBook.getValue()));
        }

        bookRepository.save(newBook);
    }

    @Override
    public List<String> allCategories() {
        List<String> strings = bookRepository.allCategories();
        return strings;
    }

    private List<BookOutDTO> mapBookDTOLists(List<Book> bookList, List<BookOutDTO> bookOutDTOList) {
        BookOutDTO bookOutDTO = new BookOutDTO();
        for (Book book : bookList) {
            modelMapper.map(book, bookOutDTO);
            BookOutDTO bookOutDTOcopy = new BookOutDTO();
            bookOutDTOcopy.clone(bookOutDTO);
            bookOutDTOList.add(bookOutDTOcopy);
        }
        return bookOutDTOList;
    }

    public boolean isBought(Book book, User user){
        List<Purchase> purchaseList = purchaseRepository.findByUser(user);
        for (Purchase purchase : purchaseList){
            if (purchase.getBook().getId() == book.getId()){
                return true;
            }
        }
        return false;
    }

    public boolean isReviewed(Book book, User user){
        List<Review> reviewList = user.getReviews();
        Book bookReview;
        for (Review review : reviewList) {
            bookReview = review.getBook();
            if (bookReview == book){
                return true;
            }
        }
        return false;
    }

}
