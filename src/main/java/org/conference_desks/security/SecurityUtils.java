package org.conference_desks.security;

import org.conference_desks.security.jwt.CustomUserPrincipal;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class SecurityUtils {

    private SecurityUtils() {
        // narzędziowa klasa - nie twórz instancji
    }

    public static Long getCurrentUserId() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new AccessDeniedException("Brak autoryzacji lub nieprawidłowy token");
        }

        return principal.getId();
    }

    public static String getCurrentUsername() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        if (auth == null || !(auth.getPrincipal() instanceof CustomUserPrincipal principal)) {
            throw new AccessDeniedException("Brak autoryzacji lub nieprawidłowy token");
        }

        return principal.getUsername();
    }
}
