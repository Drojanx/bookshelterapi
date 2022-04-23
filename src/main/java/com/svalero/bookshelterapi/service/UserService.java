package com.svalero.bookshelterapi.service;


import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.UserInDTO;
import com.svalero.bookshelterapi.dto.UserOutDTO;
import com.svalero.bookshelterapi.exception.UserNotFoundException;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    UserOutDTO addUser(UserInDTO userInDTO);
    User findUser(long id) throws UserNotFoundException;
    User findByUsername(String username);
    boolean modifyUser(User user, User formUser);
}
