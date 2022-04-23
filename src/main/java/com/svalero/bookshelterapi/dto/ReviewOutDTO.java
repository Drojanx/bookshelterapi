package com.svalero.bookshelterapi.dto;

import com.svalero.bookshelterapi.domain.Book;
import com.svalero.bookshelterapi.domain.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReviewOutDTO {

    private long id;
    private float stars;
    private String comment;
    private boolean published;
    private Book book;
    private User user;

    public ReviewOutDTO clone (ReviewOutDTO reviewOutDTO){
        this.setId(reviewOutDTO.getId());
        this.setStars(reviewOutDTO.getStars());
        this.setComment(reviewOutDTO.getComment());
        this.setPublished(reviewOutDTO.isPublished());
        this.setBook(reviewOutDTO.getBook());
        this.setUser(reviewOutDTO.getUser());
        return this;
    }
}
