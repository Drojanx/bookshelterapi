package com.svalero.bookshelterapi.domain;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name="users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(nullable = false, unique = true)
    @NotNull
    @NotBlank
    private String username;
    @Column(nullable = false)
    @NotNull
    @NotBlank
    private String password;
    @Column
    @NotNull
    @NotBlank
    private String name;
    @Column
    @NotNull
    @NotBlank
    private String surname;
    @Column(nullable = false, unique = true)
    @Email
    @NotNull
    @NotBlank
    private String email;
    @Column(name = "birth_date")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate birthDate;
    @Column(name = "creation_date")
    private LocalDate creationDate;
    @Column(name = "active")
    private boolean active;

    @ManyToMany
    @JoinTable(name = "user_role", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> roles;

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-purchases")
    List<Purchase> purchases;

    public void addPurchaseToUser(Purchase purchase) {
        purchases.add(purchase);
        purchase.setUser(this);
    }

    @OneToMany(mappedBy = "user")
    @JsonBackReference(value = "user-reviews")
    List<Review> reviews;

    public void addReviewToUser(Review review) {
        reviews.add(review);
        review.setUser(this);
    }

    @Override
    public String toString() {
        return username + '-' + name + ' ' + surname;
    }
}
