package com.svalero.bookshelterapi.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookOutDTO {

    private long id;
    private String name;
    private String author;
    private String category;
    private float price;
    private float AvgReview;

    public BookOutDTO clone (BookOutDTO bookOutDTO){
        this.setId(bookOutDTO.getId());
        this.setName(bookOutDTO.getName());
        this.setAuthor(bookOutDTO.getAuthor());
        this.setCategory(bookOutDTO.getCategory());
        this.setPrice(bookOutDTO.getPrice());
        this.setAvgReview(bookOutDTO.getAvgReview());
        return this;
    }

}
