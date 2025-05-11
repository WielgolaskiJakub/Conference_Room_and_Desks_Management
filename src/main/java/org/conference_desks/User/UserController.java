package org.conference_desks.User;


import jakarta.validation.Valid;
import org.conference_desks.security.SecurityUtils;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Validated
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.getAllUsers();
    }

    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser() {
        Long userId = SecurityUtils.getCurrentUserId();
        return ResponseEntity.ok(userService.getUserById(userId));
    }


    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable long id) {
        UserResponse user = userService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(@RequestBody @Valid UserRequest user) {
        UserResponse createdUser = userService.createUser(user);
        return ResponseEntity
                .created(URI.create("/api/v1/user/" + createdUser.getId()))
                .body(createdUser);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserFully(@PathVariable long id, @RequestBody @Valid UserRequest user) {
        UserResponse updatedUserFully = userService.updateUserFully(id, user);
        return ResponseEntity.ok(updatedUserFully);
    }

    @PreAuthorize("hasRole('ADMIN') or @userSecurity.isOwner(#id)")
    @PatchMapping("/{id}")
    public ResponseEntity<UserResponse> updateUserPartially(@PathVariable long id, @RequestBody @Valid UserPatchRequest user) {
        UserResponse updatedUserPartially = userService.updateUserPartially(id, user);
        return ResponseEntity.ok(updatedUserPartially);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable long id) {
        userService.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }
}

