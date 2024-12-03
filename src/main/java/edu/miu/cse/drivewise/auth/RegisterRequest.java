package edu.miu.cse.drivewise.auth;

import edu.miu.cse.drivewise.user.Role;

public record RegisterRequest(
        String firstName,
        String lastName,
        String email,
        String password,
        Role role
) {
}
