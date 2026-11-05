package com.Moses.Moses.POS.System.service.auth;

import com.Moses.Moses.POS.System.configuration.JwtProvider;
import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.repository.UserRepository;
import com.Moses.Moses.POS.System.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor

public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final JwtProvider jwtProvider;
    @Override
    public User getUserFromJwtToken(String token) throws UserException {
        String email = jwtProvider.getEmailFromToken(token);
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("Invalid token");
        }
        return user;
    }
    @Override
    public User getCurrentUser() throws UserException {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not found!");
        }
        return user;
    }
    @Override
    public User getUserByEmail(String email) throws UserException {
        User user = userRepository.findByEmail(email);
        if(user == null){
            throw new UserException("User not found!");
        }
        return user;
    }
    @Override
    public User getUserById(Long id) throws UserException {

        return userRepository.findById(id)
                .orElseThrow(() -> new UserException("User not found"));
    }

    @Override
    public List<User> getAllUsers() {

        return userRepository.findAll();
    }
}
