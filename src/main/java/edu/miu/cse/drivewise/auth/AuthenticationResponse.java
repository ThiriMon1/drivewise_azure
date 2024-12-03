package edu.miu.cse.drivewise.auth;

public record AuthenticationResponse(
        String token,
        String role,
        Long userId
) {
}
