package com.Moses.Moses.POS.System.service;

import com.Moses.Moses.POS.System.configuration.JwtProvider;
import com.Moses.Moses.POS.System.exception.UserException;
import com.Moses.Moses.POS.System.mapper.UserMapper;
import com.Moses.Moses.POS.System.modal.User;
import com.Moses.Moses.POS.System.repository.UserRepository;
import com.Moses.Moses.POS.System.modal.UserRole;
import com.Moses.Moses.POS.System.payload.dto.UserDto;
import com.Moses.Moses.POS.System.payload.dto.UserLoginDto;
import com.Moses.Moses.POS.System.payload.response.AuthResponse;

import com.Moses.Moses.POS.System.service.auth.AuthService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtProvider jwtProvider;
    private final CustomUserImplementation customUserImplementation;

    @Override
    public AuthResponse signup(UserDto userDto) throws UserException {

        User existingUser =
                userRepository.findByEmail(userDto.getEmail());

        if (existingUser != null) {
            throw new UserException(
                    "Email is already registered!"
            );
        }

        if (userDto.getRole() == UserRole.ROLE_ADMIN) {
            throw new UserException(
                    "Admin registration is not allowed!"
            );
        }

        User newUser = new User();

        newUser.setEmail(userDto.getEmail());

        newUser.setPassword(
                passwordEncoder.encode(userDto.getPassword())
        );

        newUser.setRole(userDto.getRole());

        newUser.setFullName(userDto.getFullName());

        newUser.setPhone(userDto.getPhone());

        newUser.setLastLogin(LocalDateTime.now());

        newUser.setCreatedAt(LocalDateTime.now());

        newUser.setUpdatedAt(LocalDateTime.now());

        User savedUser = userRepository.save(newUser);

        UserDetails userDetails =
                customUserImplementation.loadUserByUsername(
                        savedUser.getEmail()
                );

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(
                        userDetails,
                        null,
                        userDetails.getAuthorities()
                );

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        String jwt =
                jwtProvider.generateToken(authentication);

        AuthResponse authResponse =
                new AuthResponse();

        authResponse.setJwt(jwt);

        authResponse.setMessage(
                "Registered successfully!"
        );

        authResponse.setUser(
                UserMapper.toDTO(savedUser)
        );

        return authResponse;
    }

    @Override
    public AuthResponse login(UserLoginDto userLoginDto) {

        String email = userLoginDto.getEmail();

        String password = userLoginDto.getPassword();

        Authentication authentication;

        try {

            authentication = authenticate(email, password);

        } catch (UserException e) {

            throw new RuntimeException(e.getMessage());
        }

        SecurityContextHolder
                .getContext()
                .setAuthentication(authentication);

        Collection<? extends GrantedAuthority> authorities =
                authentication.getAuthorities();

        String role =
                authorities.iterator().next().getAuthority();

        String jwt =
                jwtProvider.generateToken(authentication);

        User user =
                userRepository.findByEmail(email);

        if (user == null) {

            throw new RuntimeException(
                    "User not found"
            );
        }

        user.setLastLogin(LocalDateTime.now());

        userRepository.save(user);

        AuthResponse authResponse =
                new AuthResponse();

        authResponse.setJwt(jwt);

        authResponse.setMessage(
                "Logged in successfully!"
        );

        authResponse.setUser(
                UserMapper.toDTO(user)
        );

        return authResponse;
    }

    private Authentication authenticate(
            String email,
            String password
    ) throws UserException {

        UserDetails userDetails =
                customUserImplementation
                        .loadUserByUsername(email);

        if (userDetails == null) {

            throw new UserException(
                    "Email does not exist: " + email
            );
        }

        if (!passwordEncoder.matches(
                password,
                userDetails.getPassword()
        )) {

            throw new UserException(
                    "Password does not match!"
            );
        }

        return new UsernamePasswordAuthenticationToken(
                userDetails,
                null,
                userDetails.getAuthorities()
        );
    }
}