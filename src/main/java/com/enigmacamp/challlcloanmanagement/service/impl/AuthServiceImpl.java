package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestLogin;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLogin;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import com.enigmacamp.challlcloanmanagement.repository.UserRepository;
import com.enigmacamp.challlcloanmanagement.security.jwt.JwtUtils;
import com.enigmacamp.challlcloanmanagement.service.AuthService;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;


    @Override
    public ResponseLogin login(RequestLogin request) throws BadCredentialsException {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    request.getEmail(),
                    request.getPassword()
            )
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);

        String jwt = jwtUtils.generateJwtToken(authentication);

        String role = jwtUtils.getRoleFromJwtToken(jwt);

        return ResponseLogin.builder()
                .token(jwt)
                .role(role)
                .username(request.getEmail())
                .build();
    }

    @Override
    public ResponseUser register(RequestUser request) {
        UserRole userRole = (request.getUserRole() != null && !request.getUserRole().isBlank())
                ? UserRole.valueOf(request.getUserRole())
                : UserRole.CUSTOMER;

        User newUser = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .phoneNumber(request.getPhoneNumber())
                .role(userRole)
                .build();

        User savedUser = userRepository.save(newUser);
        return MapperUser.convertToResponseUser(savedUser);
    }
}
