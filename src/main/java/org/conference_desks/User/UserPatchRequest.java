package org.conference_desks.User;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPatchRequest {
  @Email(message = "Invalid email format")
    private String email;

    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*\\d)[A-Za-z\\d]+$",
            message = "Password must contain at least one uppercase character and one number"
    )
    @NotBlank(message = "Password field cant be empty")
    @Size(min = 6, max = 32, message = "Password must be between 6 and 32 characters long")
    private String password;

}

