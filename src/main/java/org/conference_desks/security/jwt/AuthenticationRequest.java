package org.conference_desks.security.jwt;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Data
public class AuthenticationRequest {
    private String email;
    private String password;
}
