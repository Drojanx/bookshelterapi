package com.svalero.bookshelterapi.service;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.ReviewInDTO;
import com.svalero.bookshelterapi.dto.ReviewOutDTO;
import com.svalero.bookshelterapi.exception.*;

import java.util.List;

public interface ReviewService {

    ReviewOutDTO addReview(ReviewInDTO reviewInDTO, long userId) throws UserNotFoundException, BookNotFoundException, BookAlreadyReviewedException, BookNotBoughtException;
    List<Review> findByUser(User user);
    List<Review> findAllReviews();
    List<ReviewOutDTO> findReviews(User user);
    ReviewOutDTO modifyReview(long reviewId, ReviewInDTO reviewInDTO) throws ReviewNotFoundException, BookNotFoundException;
}
