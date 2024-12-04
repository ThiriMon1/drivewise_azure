package edu.miu.cse.drivewise.config;

import edu.miu.cse.drivewise.user.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.CsrfConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
      http.csrf(CsrfConfigurer::disable)
//              .cors(cors -> cors.configurationSource(request -> new org.springframework.web.cors.CorsConfiguration().applyPermitDefaultValues())) // Allow CORS
                .authorizeHttpRequests(request->
                        request.requestMatchers("/api/v1/auth/*").permitAll()
                                .requestMatchers("/api/v1/inventories").permitAll()
                                .requestMatchers("/api/v1/inventories/**").permitAll()
                                .requestMatchers("/api/v1/makes/**").permitAll()
                                .requestMatchers("/api/v1/models/**").permitAll()
                                .requestMatchers("/api/v1/reviews").hasAnyRole(Role.CUSTOMER.name(), Role.ADMIN.name())
                                .requestMatchers("/api/v1/customers").hasRole(Role.CUSTOMER.name())
                                .requestMatchers("/api/v1/offers").hasAnyRole(Role.CUSTOMER.name(), Role.ADMIN.name())
                                .requestMatchers("/api/v1/admin/**").hasRole(Role.ADMIN.name())
                                .anyRequest().authenticated())
              .authenticationProvider(authenticationProvider)
              .sessionManagement(session->session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
              .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
      return http.build();
    }
}
