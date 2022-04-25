package com.svalero.bookshelterapi.service;


import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.PatchPurchase;
import com.svalero.bookshelterapi.dto.PatchUser;
import com.svalero.bookshelterapi.dto.UserInDTO;
import com.svalero.bookshelterapi.dto.UserOutDTO;
import com.svalero.bookshelterapi.exception.*;

import java.util.List;

public interface UserService {

    List<User> findAllUsers();
    UserOutDTO addUser(UserInDTO userInDTO) throws UserModificationException;
    User findUser(long id) throws UserNotFoundException;
    UserOutDTO findUserDTO(long id) throws UserNotFoundException;
    User findByUsername(String username);
    UserOutDTO modifyUser(long userId, UserInDTO userInDTO) throws UserNotFoundException, UserModificationException;
    void deleteUser(long userId) throws UserNotFoundException;
    void patchUser(long userId, PatchUser patchUser) throws UserNotFoundException, UserModificationException;
    boolean usernameExists(String username);
    boolean emailExists(String username);
}
