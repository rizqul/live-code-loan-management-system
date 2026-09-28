package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import com.enigmacamp.challlcloanmanagement.repository.UserRepository;
import com.enigmacamp.challlcloanmanagement.service.UserService;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.InvalidParamUrlException;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.UserIdNotFoundException;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.UserNotFoundException;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    @Transactional(readOnly = false)
    public ResponseUser createUser(RequestUser request) {
        UserRole userRole = (request.getUserRole() != null && !request.getUserRole().isBlank()) ? UserRole.valueOf(request.getUserRole()) : UserRole.valueOf("CUSTOMER");

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(request.getPassword())
                .phoneNumber(request.getPhoneNumber())
                .role(userRole)
                .build();

        User savedUser = userRepository.save(user);
        return MapperUser.convertToResponseUser(savedUser);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseUser updateUser(Long id, RequestUser request) {
        if (id <= 0 ){
           throw new InvalidParamUrlException("bad path variable");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserIdNotFoundException("invalid user id"));

        String name = (request.getName() != null && !request.getName().isBlank()) ? request.getName() : user.getName();

        String email = (request.getEmail() != null && !request.getEmail().isBlank()) ? request.getEmail() : user.getEmail();

        String password = (request.getPassword() != null && !request.getPassword().isBlank()) ? request.getPassword() : user.getPassword();

        String phoneNumber = (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank()) ? request.getPhoneNumber() : user.getPhoneNumber();

        String role = (request.getUserRole() != null && !request.getUserRole().isBlank()) ? request.getUserRole().toUpperCase() : user.getRole().toString();


        if (!Objects.equals(user.getEmail(), request.getEmail())) {
            user.setEmail(email);
        }
        user.setName(name);
        user.setPhoneNumber(phoneNumber);
        user.setPassword(password);
        user.setRole(UserRole.valueOf(role));

        User updatedUser = userRepository.save(user);
        return MapperUser.convertToResponseUser(updatedUser);
    }

    @Override
    public List<ResponseUser> getAllUser() {
        List<User> users = userRepository.findAll();
        return MapperUser.convertToListResponseUser(users);
    }

    @Override
    public ResponseUser getUserById(Long id) {
        return null;
    }

    @Override
    @Transactional(readOnly = false)
    public void removeUser(Long id) {

    }
}
