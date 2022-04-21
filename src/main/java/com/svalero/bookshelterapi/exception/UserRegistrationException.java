package com.svalero.bookshelterapi.exception;

/**
 * Excepción para controlar un fallo a la hora de registrar un usuario
 */
public class UserRegistrationException extends Exception {

    public UserRegistrationException() {
        super();
    }

    public UserRegistrationException(String message) {
        super(message);
    }

}
