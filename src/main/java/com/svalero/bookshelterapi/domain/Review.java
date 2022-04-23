package com.svalero.bookshelterapi.domain;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.PositiveOrZero;
import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "reviews",
        uniqueConstraints = { @UniqueConstraint(columnNames = //Un libro solo se puede reseñar una vez por usuario
                { "user_id", "book_id" }) })
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    @PositiveOrZero @Max(10)
    private float stars;
    @Column
    private String comment;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column
    private boolean published;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

}
