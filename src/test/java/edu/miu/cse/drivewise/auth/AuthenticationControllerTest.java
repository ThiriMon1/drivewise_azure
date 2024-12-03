package edu.miu.cse.drivewise.auth;

import edu.miu.cse.drivewise.user.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
@ExtendWith(MockitoExtension.class)
class AuthenticationControllerTest {

    @Mock
    AuthenticationService authenticationService;
    @InjectMocks
    AuthenticationController authenticationController;

    @Test
    void register_validInput_returnsCreateUser() {

        RegisterRequest registerRequest=new RegisterRequest("u1","smith","u1@gmail.com","123",Role.CUSTOMER);
        AuthenticationResponse authenticationResponse=new AuthenticationResponse("sdfdfxcdsf", Role.CUSTOMER.name(), 1L);
        Mockito.when(authenticationService.registerCustomer(Mockito.any(RegisterRequest.class))).thenReturn(authenticationResponse);
        ResponseEntity<AuthenticationResponse> response=authenticationController.register(registerRequest);
        assert response.getStatusCode()== HttpStatus.CREATED;

    }

    @Test
    void authenticate_validInput_returnsAuthenticatedUser() {
        AuthenticationRequest authenticationRequest=new AuthenticationRequest("u1@gmail.com","123");

        AuthenticationResponse authenticationResponse=new AuthenticationResponse("sdfdfxcdsf", Role.CUSTOMER.name(), 1L);

        Mockito.when(authenticationService.authenticate(Mockito.any(AuthenticationRequest.class))).thenReturn(authenticationResponse);
        ResponseEntity<AuthenticationResponse> response=authenticationController.authenticate(authenticationRequest);
        assert response.getStatusCode()== HttpStatus.OK;
    }
}