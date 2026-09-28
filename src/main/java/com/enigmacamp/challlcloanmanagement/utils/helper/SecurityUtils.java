package com.enigmacamp.challlcloanmanagement.utils.helper;

import com.enigmacamp.challlcloanmanagement.entity.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class SecurityUtils {

    public User getCurrentUser() {
        Authentication authentication =
                SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null ||
                !authentication.isAuthenticated() ||
                authentication.getPrincipal() instanceof String) {
            return null;
        }

        return (User) authentication.getPrincipal();
    }

    public Long getCurrentUserId() {
        User user = getCurrentUser();

        return user != null ? user.getId() : null;
    }
}
