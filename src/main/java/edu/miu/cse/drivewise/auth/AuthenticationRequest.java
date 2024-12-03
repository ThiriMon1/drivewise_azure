package edu.miu.cse.drivewise.auth;

public record AuthenticationRequest(
        String email,
        String password
) {

}
