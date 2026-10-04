package com.enigmacamp.challlcloanmanagement.service.impl;

import com.enigmacamp.challlcloanmanagement.dto.request.RequestUser;
import com.enigmacamp.challlcloanmanagement.dto.request.RequestUserProfile;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseDocument;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseLoan;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUser;
import com.enigmacamp.challlcloanmanagement.dto.response.ResponseUserProfile;
import com.enigmacamp.challlcloanmanagement.entity.Document;
import com.enigmacamp.challlcloanmanagement.entity.Loan;
import com.enigmacamp.challlcloanmanagement.entity.User;
import com.enigmacamp.challlcloanmanagement.entity.UserProfile;
import com.enigmacamp.challlcloanmanagement.entity.enums.UserRole;
import com.enigmacamp.challlcloanmanagement.repository.UserRepository;
import com.enigmacamp.challlcloanmanagement.service.DocumentService;
import com.enigmacamp.challlcloanmanagement.service.UserProfileService;
import com.enigmacamp.challlcloanmanagement.service.UserService;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.InvalidParamUrlException;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.UserAlreadyHaveUserProfileException;
import com.enigmacamp.challlcloanmanagement.utils.exceptions.UserNotFoundException;
import com.enigmacamp.challlcloanmanagement.utils.helper.SecurityUtils;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperLoan;
import com.enigmacamp.challlcloanmanagement.utils.mapper.MapperUser;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.BadRequestException;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Objects;

@Service
@Transactional(readOnly = true)
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final DocumentService documentService;
    private final UserProfileService userProfileService;
    private final SecurityUtils securityUtils;

    @Override
    @Transactional(readOnly = false)
    public ResponseUser createUser(RequestUser request) {
        UserRole userRole = (request.getUserRole() != null && !request.getUserRole().isBlank())
                ? UserRole.valueOf(request.getUserRole())
                : UserRole.valueOf("CUSTOMER");

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

        if (id <= 0) {
            throw new InvalidParamUrlException("bad path variable");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("invalid user id"));

        String name = (request.getName() != null && !request.getName().isBlank()) ? request.getName() : user.getName();

        String email = (request.getEmail() != null && !request.getEmail().isBlank()) ? request.getEmail()
                : user.getEmail();

        String password = (request.getPassword() != null && !request.getPassword().isBlank()) ? request.getPassword()
                : user.getPassword();

        String phoneNumber = (request.getPhoneNumber() != null && !request.getPhoneNumber().isBlank())
                ? request.getPhoneNumber()
                : user.getPhoneNumber();

        String role = (request.getUserRole() != null && !request.getUserRole().isBlank())
                ? request.getUserRole().toUpperCase()
                : user.getRole().toString();

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

        if (id <= 0) {
            throw new InvalidParamUrlException("bad path variable");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("invalid user id"));

        return MapperUser.convertToResponseUser(user);
    }

    @Override
    @Transactional(readOnly = false)
    public ResponseUser removeUser(Long id) {
        if (id <= 0) {
            throw new InvalidParamUrlException("bad path variable");
        }

        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("invalid user id"));

        userRepository.deleteById(id);
        return MapperUser.convertToResponseUser(user);
    }

    @Override
    public List<ResponseLoan> getUserLoans(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("invalid user id"));

        List<Loan> loans = user.getLoans();
        return MapperLoan.convertToListResponseLoan(loans);
    }

    @Override
    @Transactional
    public ResponseUserProfile uploadDocument(MultipartFile document, String documentType) {
        Long id = securityUtils.getCurrentUserId();

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));

        Long userProfileId = user.getUserProfile().getId();

        Document saveDocument = documentService.store(document, documentType);

        RequestUserProfile updatedColumn ;

        if (documentType.equalsIgnoreCase("KTP")){
            updatedColumn = RequestUserProfile.builder()
                    .ktpPath(saveDocument.getDocumentPath())
                    .build();
        } else if (documentType.equalsIgnoreCase("SALARY")) {
            updatedColumn = RequestUserProfile.builder()
                    .salarySlipPath(saveDocument.getDocumentPath())
                    .build();
        } else {
            throw new InvalidParamUrlException("Invalid document Type");
        }


        ResponseUserProfile updateUserProfile = userProfileService.update(userProfileId, updatedColumn);

        return updateUserProfile;
    }

    @Override
    public ResponseDocument downloadDocument(String documentType) {

        User currentUser = securityUtils.getCurrentUser();


        String fileUri;

        if (documentType.equalsIgnoreCase("KTP")){
            fileUri = currentUser.getUserProfile().getKtpPath();
        } else if (documentType.equalsIgnoreCase("SALARY")) {
            fileUri = currentUser.getUserProfile().getSalarySlipPath();
        } else {
            throw new InvalidParamUrlException("Invalid document Type");
        }

        Document document = documentService.getFileByPath(fileUri);

        log.error("ini adalah document path nya : " + document.getDocumentPath());

        Resource resource = documentService.loadFileAsResource(fileUri);

        log.error("ini resousrce nua : " + resource.isFile());

        ResponseDocument response = ResponseDocument.builder()
                .resource(resource)
                .documentType(document.getDocumentType())
                .documentSize(document.getDocumentSize())
                .documentExtension(document.getDocumentExtension())
                .documentPath(document.getDocumentPath())
                .documentName(document.getGeneratedName())
                .build();


        return response;
    }

    @Override
    @Transactional
    public ResponseUser createUserProfile(RequestUserProfile request) {
        Long id = securityUtils.getCurrentUserId();

        User user = userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("user not found"));

        if (user.getUserProfile() != null) {
            throw new UserAlreadyHaveUserProfileException("user already create user profile");
        }

        UserProfile userProfile = userProfileService.create(request);

        user.setUserProfile(userProfile);

        User updatedUser = userRepository.save(user);

        return MapperUser.convertToResponseUser(updatedUser);

    }
}
