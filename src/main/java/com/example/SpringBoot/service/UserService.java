package com.example.SpringBoot.service;

import com.example.SpringBoot.dto.UserDTO;
import com.example.SpringBoot.model.Role;
import com.example.SpringBoot.model.User;
import com.example.SpringBoot.repository.RoleRepository;
import com.example.SpringBoot.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public void saveUser(UserDTO userDTO) {
        Role role = roleRepository.findByName(userDTO.getRole());

        if (role == null)
            role = roleRepository.save(new Role(userDTO.getRole()));

        User user = new User(userDTO.getName(), userDTO.getEmail(), passwordEncoder.encode(userDTO.getPassword()),
                Arrays.asList(role));
        userRepository.save(user);
    }

    public User findUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}