package com.svalero.bookshelterapi.service.impl;


import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.PatchReview;
import com.svalero.bookshelterapi.dto.ReviewInDTO;
import com.svalero.bookshelterapi.dto.ReviewOutDTO;
import com.svalero.bookshelterapi.exception.*;
import com.svalero.bookshelterapi.repository.ReviewRepository;
import com.svalero.bookshelterapi.service.BookService;
import com.svalero.bookshelterapi.service.ReviewService;
import com.svalero.bookshelterapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class ReviewServiceImpl implements ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private UserService userService;
    @Autowired
    private BookService bookService;

    @Override
    public ReviewOutDTO addReview(ReviewInDTO reviewInDTO, long userId) throws UserNotFoundException, BookNotFoundException, BookAlreadyReviewedException, BookNotBoughtException {
        Review review = new Review();
        Book book = bookService.findBook(reviewInDTO.getBookId());
        User user = userService.findUser(userId);
        review.setCreationDate(LocalDate.now());
        review.setStars(reviewInDTO.getStars());
        review.setComment(reviewInDTO.getComment());
        review.setPublished(reviewInDTO.isPublished());
        if(review.isPublished()){
            review.setPublishedDate(LocalDate.now());
        }
        review.setUser(user);
        review.setBook(book);
        if (bookService.isBought(book, user)){
            if(bookService.isReviewed(book, user)){
                throw new BookAlreadyReviewedException();
            } else {
                user.addReviewToUser(review);
            }
        } else {
            throw new BookNotBoughtException();
        }

        Review newReview = reviewRepository.save(review);

        ReviewOutDTO reviewOutDTO = new ReviewOutDTO();
        modelMapper.map(newReview, reviewOutDTO);
        return reviewOutDTO;
    }



    @Override
    public ReviewOutDTO modifyReview(long reviewId, ReviewInDTO reviewInDTO) throws ReviewNotFoundException, BookNotFoundException {
        Review newReview = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        Book book = bookService.findBook(reviewInDTO.getBookId());
        newReview.setBook(book);
        newReview.setPublished(reviewInDTO.isPublished());
        if(newReview.isPublished()){
            newReview.setPublishedDate(LocalDate.now());
        }
        newReview.setComment(reviewInDTO.getComment());
        newReview.setStars(reviewInDTO.getStars());
        reviewRepository.save(newReview);

        ReviewOutDTO newReviewOutDTO = new ReviewOutDTO();
        modelMapper.map(newReview, newReviewOutDTO);

        return newReviewOutDTO;
    }

    @Override
    public void deleteReview(long reviewId) throws ReviewNotFoundException{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        reviewRepository.delete(review);
    }

    @Override
    public void patchReview(long reviewId, PatchReview patchReview) throws ReviewNotFoundException {
        Review newReview = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        if (patchReview.getField().equals("stars")){
            newReview.setStars(Float.parseFloat(patchReview.getValue()));
        }
        reviewRepository.save(newReview);
    }

    @Override
    public List<Review> findByUser(User user) {
        return reviewRepository.findByUser(user);
    }

    @Override
    public ReviewOutDTO findById(long reviewId) throws ReviewNotFoundException{
        Review review = reviewRepository.findById(reviewId)
                .orElseThrow(ReviewNotFoundException::new);
        ReviewOutDTO reviewOutDTO = new ReviewOutDTO();
        modelMapper.map(review, reviewOutDTO);
        return reviewOutDTO;
    }

    @Override
    public List<Review> findAllReviews() {
        return null;
    }

    @Override
    public List<ReviewOutDTO> findReviews(User user) {
        List<Review> reviewList = reviewRepository.findByUser(user);

        List<ReviewOutDTO> reviewOutDTOList = new ArrayList<ReviewOutDTO>();
        ReviewOutDTO reviewOutDTO = new ReviewOutDTO();
        for (Review review : reviewList){
            modelMapper.map(review, reviewOutDTO);
            ReviewOutDTO reviewOutDTOcopy = new ReviewOutDTO();
            reviewOutDTOcopy.clone(reviewOutDTO);
            reviewOutDTOList.add(reviewOutDTOcopy);
        }
        return reviewOutDTOList;
    }


}
