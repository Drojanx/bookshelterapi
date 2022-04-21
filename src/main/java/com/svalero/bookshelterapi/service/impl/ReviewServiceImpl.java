package com.svalero.bookshelterapi.service.impl;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.repository.ReviewRepository;
import com.svalero.bookshelterapi.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    @Override
    public boolean addReview(Review review) {
        try{
            review.setCreationDate(LocalDate.now());
            reviewRepository.save(review);
        } catch (DataIntegrityViolationException ex){
            return false;
        }
        return true;
    }

    @Override
    public boolean modifyReview(Review review, Review formReview) {
        try{
            review.setComment(formReview.getComment());
            review.setStars(formReview.getStars());
            review.setPublished(formReview.isPublished());
            reviewRepository.save(review);
        } catch (DataIntegrityViolationException ex){
            return false;
        }
        return true;
    }

    @Override
    public List<Review> findByUser(User user) {
        return reviewRepository.findByUser(user);
    }

    @Override
    public List<Review> findByBook(Book book) {
        return reviewRepository.findByBook(book);
    }

    @Override
    public List<Review> findAllReviews() {
        return null;
    }

    @Override
    public List<Review> findPurchases(User user) {
        return null;
    }

    @Override
    public Review findByUserAndBook(User user, Book book) {
        return reviewRepository.findByUserAndBook(user, book);
    }


}
