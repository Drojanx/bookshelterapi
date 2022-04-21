package com.svalero.bookshelterapi.service;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;

import java.util.List;

public interface ReviewService {

    boolean addReview(Review review);
    List<Review> findByUser(User user);
    List<Review> findByBook(Book book);
    List<Review> findAllReviews();
    List<Review> findPurchases(User user);
    Review findByUserAndBook(User user, Book book);
    boolean modifyReview(Review review, Review formReview);
}
