package org.conference_desks.User;

import jakarta.validation.constraints.*;
import lombok.Getter;
import lombok.Setter;
import org.conference_desks.common.Role;
@Getter
@Setter
public class UserRequest {

    Long id;

    @Email
    @NotBlank(message = "Email field cant be empty")
    String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "Password must contain at least one uppercase character and one number"
    )
    @NotBlank(message = "Password field cant be empty")
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters long")
    String password;

    @NotNull(message = "Field role can't be empty")
    Role role;

    @NotBlank(message = "Field departament cant be empty")
    String department;

}
