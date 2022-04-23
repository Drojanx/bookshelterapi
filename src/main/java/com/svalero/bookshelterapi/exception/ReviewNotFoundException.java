package com.svalero.bookshelterapi.exception;

public class BookNotFoundException extends Exception{

    public BookNotFoundException(String message) {
        super(message);
    }

    public BookNotFoundException(long bookId){
        super("Libro "+bookId+" no encontrado");
    }

    public BookNotFoundException() {
        super("Libro no encontrado");
    }
}
