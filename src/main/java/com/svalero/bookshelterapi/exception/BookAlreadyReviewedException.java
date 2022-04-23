package com.svalero.bookshelterapi.exception;

public class BookAlreadyReviewedException extends Exception{

    public BookAlreadyReviewedException(String message) {
        super(message);
    }

    public BookAlreadyReviewedException(long bookId){
        super("Libro "+bookId+" ya ha sido reseñado previamente");
    }

    public BookAlreadyReviewedException() {
        super("Libro ya ha sido previamente reseñado previamente");
    }
}
