package com.svalero.bookshelterapi.repository;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ReviewRepository extends CrudRepository<Review, Long> {

    List<Review> findAll();
    List<Review> findByUser(User user);
    Review findByUserAndBook(User user, Book book);
    List<Review> findByBook(Book book);
}
