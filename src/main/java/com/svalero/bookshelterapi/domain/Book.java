package com.svalero.bookshelterapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @NotBlank
    private String name;
    @Column
    @NotBlank
    private String author;
    @Column
    @NotBlank
    private String category;
    @Column
    @PositiveOrZero
    private float price;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column
    public float getAvgReview(){
        float sumReviews = 0;
        int numOfPublished = 0;
        if (reviews != null) {
            for (Review review : reviews) {
                if (review.isPublished()) {
                    numOfPublished++;
                    sumReviews += review.getStars();
                }
            }
        }
        if (sumReviews == 0 && numOfPublished == 0)
            return 0;
        return sumReviews/numOfPublished;
    }

    @Override
    public String toString() {
        return "------------------------------------------------------------"+ "\n"
                +name + " - " + author;
    }

    @OneToMany(mappedBy = "book")
    @JsonBackReference(value = "book-purchases")
    private List<Purchase> purchases;
    @OneToMany(mappedBy = "book")
    @JsonBackReference(value = "book-reviewsD")
    private List<Review> reviews;
}
