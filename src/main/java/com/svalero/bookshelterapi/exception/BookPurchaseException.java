package com.svalero.bookshelterapi.exception;

public class BookPurchaseException extends Exception{

    public BookPurchaseException(String message) {
        super(message);
    }

    public BookPurchaseException() {
        super();
    }
}
