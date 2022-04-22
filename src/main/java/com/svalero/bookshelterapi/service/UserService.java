package com.svalero.bookshelterapi.service;


import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    User addUser(User user);
    User findUser(long id) throws UserNotFoundException;
    User findByUsername(String username);
    boolean modifyUser(User user, User formUser);
}
