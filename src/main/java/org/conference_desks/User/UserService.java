package org.conference_desks.User;

import org.conference_desks.globalExceptionHandler.ApiException;
import org.conference_desks.globalExceptionHandler.ErrorCode;
import org.conference_desks.reservation.ReservationResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserResponse> getAllUsers() {

        return userRepository.findAll().stream().map(this::mapToResponse).toList();
    }

    public UserResponse getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, id));
        return mapToResponse(user);
    }

    public UserResponse createUser(UserRequest userRequest) {
        User user = new User();
        User savedUser = updateUserFromRequest(user, userRequest);
        return mapToResponse(savedUser);
    }

    //user
    public UserResponse updateUserPartially(Long id, UserPatchRequest userPatchRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, id));
        User savedUser = updateUserFromPatchRequest(user, userPatchRequest);
        return mapToResponse(savedUser);

    }
//admin only
    public UserResponse updateUserFully(Long id, UserRequest userRequest) {
        User user = userRepository.findById(id).orElseThrow(() -> new ApiException(ErrorCode.USER_NOT_FOUND, id));
        User savedUser = updateUserFromRequest(user, userRequest);
        return mapToResponse(savedUser);
    }

    public void deleteUserById(Long id) {
        if (!userRepository.existsById(id)) {
            throw new ApiException(ErrorCode.USER_NOT_FOUND, id);
        }
        userRepository.deleteById(id);
    }

    private User updateUserFromRequest(User user, UserRequest userRequest) {
        user.setEmail(userRequest.getEmail());
        user.setPassword(userRequest.getPassword());
        user.setDepartment(userRequest.getDepartment());
        user.setRole(userRequest.getRole());

        return userRepository.save(user);
    }

    private User updateUserFromPatchRequest(User user, UserPatchRequest userPatchRequest) {
        if (userPatchRequest.getEmail() != null) {
            user.setEmail(userPatchRequest.getEmail());
        }
        if (userPatchRequest.getPassword() != null) {
            user.setPassword(userPatchRequest.getPassword());
        }
        return userRepository.save(user);
    }

    public UserResponse mapToResponse(User user) {
        List<ReservationResponse> reservationResponses = user.getReservations().stream()
                .map(res -> new ReservationResponse(
                        res.getId(),
                        res.getStartTime(),
                        res.getEndTime(),
                        res.getTitle(),
                        res.getType(),
                        res.getRoom() != null ? res.getRoom().getId() : null,
                        res.getDesk() != null ? res.getDesk().getId() : null
                )).toList();

        return new UserResponse(
                user.getId(),
                user.getEmail(),
                user.getDepartment(),
                reservationResponses
        );
    }
}
