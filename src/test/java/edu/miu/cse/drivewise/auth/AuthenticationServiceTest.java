package edu.miu.cse.drivewise.auth;

import edu.miu.cse.drivewise.config.JwtService;
import edu.miu.cse.drivewise.model.Customer;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.user.Role;
import edu.miu.cse.drivewise.user.User;
import edu.miu.cse.drivewise.user.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

@ExtendWith(MockitoExtension.class)
class AuthenticationServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthenticationService authenticationService;

    private RegisterRequest mockRegisterRequestAdmin;
    private RegisterRequest mockRegisterRequestCustomer;
    private AuthenticationRequest mockAuthenticationRequest;

    @BeforeEach
    void setUp() {
        mockRegisterRequestAdmin = new RegisterRequest("John", "Doe", "admin@example.com", "password", Role.ADMIN);
        mockRegisterRequestCustomer = new RegisterRequest("Jane", "Doe", "customer@example.com", "password", Role.CUSTOMER);
        mockAuthenticationRequest = new AuthenticationRequest("admin@example.com", "password");
    }

    @Test
    void registerAdmin() {
        User user = new User("John", "Doe", "admin@example.com", "encodedPassword", Role.ADMIN);
        Mockito.when(passwordEncoder.encode(mockRegisterRequestAdmin.password())).thenReturn("encodedPassword");
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("mockJwtToken");

        AuthenticationResponse response = authenticationService.registerAdmin(mockRegisterRequestAdmin);

        assertNotNull(response);
        assertEquals("mockJwtToken", response.token());
        assertEquals(Role.ADMIN.name(), response.role());
        Mockito.verify(userRepository).save(any(User.class));
        Mockito.verify(jwtService).generateToken(user);
    }

    @Test
    void registerCustomer() {
        Customer customer = new Customer("Jane", "Doe", "customer@example.com", "encodedPassword", Role.CUSTOMER);
        Mockito.when(passwordEncoder.encode(mockRegisterRequestCustomer.password())).thenReturn("encodedPassword");
        Mockito.when(customerRepository.save(any(Customer.class))).thenReturn(customer);
        Mockito.when(jwtService.generateToken(customer)).thenReturn("mockJwtToken");

        AuthenticationResponse response = authenticationService.registerCustomer(mockRegisterRequestCustomer);

        assertNotNull(response);
        assertEquals("mockJwtToken", response.token());
        assertEquals(Role.CUSTOMER.name(), response.role());
        Mockito.verify(customerRepository).save(any(Customer.class));
        Mockito.verify(jwtService).generateToken(customer);
    }

    @Test
    void authenticate() {
        User user = new User("John", "Doe", "admin@example.com", "encodedPassword", Role.ADMIN);
        Authentication mockAuthentication = Mockito.mock(Authentication.class);

        Mockito.when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class))).thenReturn(mockAuthentication);
        Mockito.when(mockAuthentication.getPrincipal()).thenReturn(user);
        Mockito.when(jwtService.generateToken(user)).thenReturn("mockJwtToken");

        AuthenticationResponse response = authenticationService.authenticate(mockAuthenticationRequest);

        assertNotNull(response);
        assertEquals("mockJwtToken", response.token());
        assertEquals(Role.ADMIN.name(), response.role());
        Mockito.verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        Mockito.verify(jwtService).generateToken(user);
    }
}
