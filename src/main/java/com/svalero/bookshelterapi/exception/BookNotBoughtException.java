package com.svalero.bookshelterapi.exception;

public class BookNotBoughtException extends Exception{

    public BookNotBoughtException(String message) {
        super(message);
    }

    public BookNotBoughtException(long bookId){
        super("Libro "+bookId+" no comprado previamente");
    }

    public BookNotBoughtException() {
        super("Libro no comprado previamente");
    }
}
