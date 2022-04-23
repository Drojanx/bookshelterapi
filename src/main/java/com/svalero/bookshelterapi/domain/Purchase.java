package com.svalero.bookshelterapi.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "purchases",
        uniqueConstraints = { @UniqueConstraint(columnNames = //Un libro solo se puede comprar una vez por usuario
                { "user_id", "book_id" }) })
public class Purchase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(name = "creation_date")
    private LocalDate creationDate;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;

    @Override
    public String toString() {
        return book.toString()+"\n"+
                "Código de compra: " + id +"\n"+
                "Precio: " + book.getPrice()+ "\n" +
                "Fecha de compra: " + creationDate;
    }
}
