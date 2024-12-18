package edu.miu.cse.drivewise.auth;

import edu.miu.cse.drivewise.config.JwtService;
import edu.miu.cse.drivewise.exception.user.DuplicateEmailException;
import edu.miu.cse.drivewise.model.Customer;
import edu.miu.cse.drivewise.repository.CustomerRepository;
import edu.miu.cse.drivewise.user.Role;
import edu.miu.cse.drivewise.user.User;
import edu.miu.cse.drivewise.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final CustomerRepository customerRepository;

    public AuthenticationResponse registerAdmin(RegisterRequest registerRequest){
        User user = new User(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password()),
                Role.ADMIN
        );
        try {
            User registeredUser = userRepository.save(user);
            String token = jwtService.generateToken(registeredUser);
            return new AuthenticationResponse(token, registerRequest.role().name(), registeredUser.getUserId());
        }catch (DataIntegrityViolationException e) {
            // Check if it's a duplicate email error
            if (e.getMessage().contains("users_email_key")) {
                throw new DuplicateEmailException("The email " + user.getEmail() + " is already registered.");
            }
            throw e;
        }
    }

    public AuthenticationResponse registerCustomer(RegisterRequest registerRequest){
        Customer customer = new Customer(
                registerRequest.firstName(),
                registerRequest.lastName(),
                registerRequest.email(),
                passwordEncoder.encode(registerRequest.password()),
                registerRequest.role()
        );
        try{
            Customer registeredCustomer = customerRepository.save(customer);

        String token = jwtService.generateToken(registeredCustomer);
        return new AuthenticationResponse(token,registerRequest.role().name(),registeredCustomer.getUserId());
        }catch (DataIntegrityViolationException e){
            // Check if it's a duplicate email error
            if (e.getMessage().contains("users_email_key")) {
                throw new DuplicateEmailException("The email " + customer.getEmail() + " is already registered.");
            }
            throw e;
        }
    }

    public AuthenticationResponse authenticate(AuthenticationRequest authenticationRequest){
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    authenticationRequest.email(),
                    authenticationRequest.password()
            )
        );
        User user = (User) authentication.getPrincipal();
        String token = jwtService.generateToken(user);
        return new AuthenticationResponse(token,user.getRole().name(),user.getUserId());
    }

}
