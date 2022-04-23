package com.svalero.bookshelterapi.exception;

public class BookAlreadyBoughtException extends Exception{

    public BookAlreadyBoughtException(String message) {
        super(message);
    }

    public BookAlreadyBoughtException(long bookId){
        super("Libro "+bookId+" ya ha sido previamente");
    }

    public BookAlreadyBoughtException() {
        super("Libro ya ha sido previamente comprado previamente");
    }
}
