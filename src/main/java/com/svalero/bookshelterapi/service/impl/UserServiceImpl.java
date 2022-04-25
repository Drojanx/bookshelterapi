package com.svalero.bookshelterapi.service.impl;


import com.svalero.bookshelterapi.domain.Purchase;
import com.svalero.bookshelterapi.domain.Review;
import com.svalero.bookshelterapi.domain.Role;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.dto.PatchUser;
import com.svalero.bookshelterapi.dto.UserInDTO;
import com.svalero.bookshelterapi.dto.UserOutDTO;
import com.svalero.bookshelterapi.exception.PurchaseNotFoundException;
import com.svalero.bookshelterapi.exception.ReviewNotFoundException;
import com.svalero.bookshelterapi.exception.UserModificationException;
import com.svalero.bookshelterapi.exception.UserNotFoundException;
import com.svalero.bookshelterapi.repository.RoleRepository;
import com.svalero.bookshelterapi.repository.UserRepository;
import com.svalero.bookshelterapi.security.Constants;
import com.svalero.bookshelterapi.service.PurchaseService;
import com.svalero.bookshelterapi.service.ReviewService;
import com.svalero.bookshelterapi.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public UserOutDTO addUser(UserInDTO userInDTO) throws UserModificationException {
        UserOutDTO userOutDTO = new UserOutDTO();
        try{
            User user = new User();
            user.setPassword(userInDTO.getPassword()); //TODO securizar password
            user.setCreationDate(LocalDate.now());
            user.setActive(true);
            if(usernameExists(userInDTO.getUsername()))
                throw new UserModificationException("Nombre de usuario ya en uso");
            user.setUsername(userInDTO.getUsername());
            user.setPassword(userInDTO.getPassword());
            if(emailExists(userInDTO.getEmail()))
                throw new UserModificationException("Email ya en uso");
            user.setEmail(userInDTO.getEmail());
            user.setName(userInDTO.getName());
            user.setSurname(userInDTO.getSurname());
            user.setBirthDate(userInDTO.getBirthDate());
            Role userRole = roleRepository.findByName(Constants.USER_ROLE);
            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            User newUser = userRepository.save(user);
            modelMapper.map(newUser, userOutDTO);
        } catch (DataIntegrityViolationException ex){}
        return userOutDTO;
    }

    @Override
    public User findUser(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public UserOutDTO findUserDTO(long id) throws UserNotFoundException {
        User user = userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
        UserOutDTO userOutDTO = new UserOutDTO();
        modelMapper.map(user, userOutDTO);
        return userOutDTO;
    }

    @Override
    public User findByUsername(String username) { return userRepository.findByUsername(username); }

    @Override
    public UserOutDTO modifyUser(long userId, UserInDTO userInDTO) throws UserNotFoundException, UserModificationException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        user.setPassword(userInDTO.getPassword()); //TODO securizar password
        user.setName(userInDTO.getName());
        if(usernameExists(userInDTO.getUsername()))
            if(!user.getUsername().equals(userInDTO.getUsername()))
                throw new UserModificationException("Nombre de usuario ya en uso");
        user.setSurname(userInDTO.getSurname());
        user.setBirthDate(userInDTO.getBirthDate());
        if(emailExists(userInDTO.getEmail()))
            if(!user.getEmail().equals(userInDTO.getEmail()))
                throw new UserModificationException("Email ya en uso");
        user.setEmail(userInDTO.getEmail());
        user.setUsername(userInDTO.getUsername());
        user.setActive(userInDTO.isActive());
        userRepository.save(user);

        UserOutDTO userOutDTO = new UserOutDTO();
        modelMapper.map(user, userOutDTO);
        return userOutDTO;
    }

    @Override
    public void deleteUser(long userId) throws UserNotFoundException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        userRepository.delete(user);
    }

    @Override
    public void patchUser(long userId, PatchUser patchUser) throws UserNotFoundException, UserModificationException {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);
        if (patchUser.getField().equals("username")){
            String username = patchUser.getValue();
                if(usernameExists(username))
                    if(!user.getUsername().equals(username))
                        throw new UserModificationException("Nombre de usuario ya en uso");
            user.setUsername(patchUser.getValue());
        }
        userRepository.save(user);
    }

    @Override
    public boolean usernameExists(String username) {
        return userRepository.existsByUsername(username);
    }

    @Override
    public boolean emailExists(String email) {
        return userRepository.existsByEmail(email);
    }
}
