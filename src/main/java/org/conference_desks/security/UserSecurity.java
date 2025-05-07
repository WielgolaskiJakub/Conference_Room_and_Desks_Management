package org.conference_desks.security;


import org.conference_desks.User.UserRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Component
public class UserSecurity {

    private final UserRepository userRepository;

    public UserSecurity(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public boolean isOwner(Long userId) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String currentUsername = authentication.getName(); // Login aktualnie zalogowanego usera

        return userRepository.findById(userId)
                .map(user -> user.getUsername().equals(currentUsername))
                .orElse(false);
    }
}
