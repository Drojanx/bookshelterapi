package com.svalero.bookshelterapi.service;


import com.svalero.bookshelterapi.domain.User;

public interface UserService {

    User addUser(User user);
    User findUser(long id);
    User findByUsername(String username);
    boolean modifyUser(User user, User formUser);
}
