package com.svalero.bookshelterapi.exception;

public class PurchaseNotFoundException extends Exception{

    public PurchaseNotFoundException(String message) {
        super(message);
    }

    public PurchaseNotFoundException(long purchaseId){
        super("Compra "+purchaseId+" no encontrada");
    }

    public PurchaseNotFoundException() {
        super("Compra no encontrada");
    }
}
