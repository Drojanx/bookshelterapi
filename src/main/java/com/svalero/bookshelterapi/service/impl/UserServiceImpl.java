package com.svalero.bookshelterapi.service.impl;


import com.svalero.bookshelterapi.domain.Role;
import com.svalero.bookshelterapi.domain.User;
import com.svalero.bookshelterapi.exception.UserNotFoundException;
import com.svalero.bookshelterapi.repository.RoleRepository;
import com.svalero.bookshelterapi.repository.UserRepository;
import com.svalero.bookshelterapi.security.Constants;
import com.svalero.bookshelterapi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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

    @Override
    public List<User> findAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User addUser(User user) {
/*        try{
            BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
            user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
            user.setCreationDate(LocalDate.now());
            user.setActive(true);
            Role userRole = roleRepository.findByName(Constants.USER_ROLE);
            user.setRoles(new HashSet<>(Collections.singletonList(userRole)));
            userRepository.save(user);
        } catch (DataIntegrityViolationException ex){
            return false;
        }*/
        return userRepository.save(user);
    }

    @Override
    public User findUser(long id) throws UserNotFoundException {
        return userRepository.findById(id)
                .orElseThrow(UserNotFoundException::new);
    }

    @Override
    public User findByUsername(String username) { return userRepository.findByUsername(username); }

    @Override
    public boolean modifyUser(User user, User formUser) {
        try{
            user.setName(formUser.getName());
            user.setSurname(formUser.getSurname());
            user.setBirthDate(formUser.getBirthDate());
            user.setEmail(formUser.getEmail());
            user.setUsername(formUser.getUsername());
            userRepository.save(user);

        } catch (DataIntegrityViolationException ex){
            return false;
        }
        return true;
    }
}
