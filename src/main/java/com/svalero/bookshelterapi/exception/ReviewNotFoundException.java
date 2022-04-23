package com.svalero.bookshelterapi.exception;

public class ReviewNotFoundException extends Exception{

    public ReviewNotFoundException(String message) {
        super(message);
    }

    public ReviewNotFoundException(long bookId){
        super("Review "+bookId+" no encontrada");
    }

    public ReviewNotFoundException() {
        super("Review no encontrada");
    }
}
