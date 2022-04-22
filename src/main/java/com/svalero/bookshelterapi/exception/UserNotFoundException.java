package com.svalero.bookshelterapi.exception;

import com.svalero.bookshelterapi.domain.User;

public class UserNotFoundException extends Exception{

    public UserNotFoundException(String message){ super(message);}

    public UserNotFoundException(long userId){ super("Usuario " + userId + " no encontrado");}

    public UserNotFoundException(){ super("Usuario no encontrado");}

}
