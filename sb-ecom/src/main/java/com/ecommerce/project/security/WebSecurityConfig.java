package com.ecommerce.project.security;

import com.ecommerce.project.model.AppRole;
import com.ecommerce.project.model.Role;
import com.ecommerce.project.model.User;
import com.ecommerce.project.repositories.RoleRepository;
import com.ecommerce.project.repositories.UserRepository;
import com.ecommerce.project.security.jwt.AuthEntryPointJwt;
import com.ecommerce.project.security.jwt.AuthTokenFilter;
import com.ecommerce.project.security.services.UserDetailsServiceImpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.HashSet;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Autowired
    private AuthEntryPointJwt unauthorizedHandler;


    // --------------------------------------------------
    // JWT FILTER
    // --------------------------------------------------

    @Bean
    public AuthTokenFilter authenticationJwtTokenFilter() {
        return new AuthTokenFilter();
    }


    // --------------------------------------------------
    // PASSWORD ENCODER
    // --------------------------------------------------

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    // --------------------------------------------------
    // AUTHENTICATION PROVIDER
    // --------------------------------------------------

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {

        DaoAuthenticationProvider authProvider =
                new DaoAuthenticationProvider(userDetailsService);

        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }


    // --------------------------------------------------
    // AUTHENTICATION MANAGER
    // --------------------------------------------------

    @Bean
    public AuthenticationManager authenticationManager(
            org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration authConfig)
            throws Exception {

        return authConfig.getAuthenticationManager();
    }


    // --------------------------------------------------
    // SECURITY FILTER CHAIN
    // --------------------------------------------------

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
                // Disable CSRF because this is a stateless JWT API
                .csrf(csrf -> csrf.disable())

                // Handle unauthorized requests
                .exceptionHandling(exception ->
                        exception.authenticationEntryPoint(unauthorizedHandler)
                )

                // JWT authentication = stateless
                .sessionManagement(session ->
                        session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )

                // Authorization rules
                .authorizeHttpRequests(auth -> auth

                        // Authentication endpoints
                        .requestMatchers("/api/auth/**").permitAll()

                        // Swagger / OpenAPI
                        .requestMatchers("/v3/api-docs/**").permitAll()
                        .requestMatchers("/swagger-ui/**").permitAll()

                        // H2 console
                        .requestMatchers("/h2-console/**").permitAll()

                        // Test endpoints
                        .requestMatchers("/api/test/**").permitAll()

                        // Static images
                        .requestMatchers("/images/**").permitAll()

                        // Everything else requires authentication
                        .anyRequest().authenticated()
                )

                // Use our DaoAuthenticationProvider
                .authenticationProvider(authenticationProvider())

                // JWT filter must run before username/password filter
                .addFilterBefore(
                        authenticationJwtTokenFilter(),
                        UsernamePasswordAuthenticationFilter.class
                )

                // Required for H2 console iframe
                .headers(headers ->
                        headers.frameOptions(frameOptions ->
                                frameOptions.sameOrigin()
                        )
                );

        return http.build();
    }


    // --------------------------------------------------
    // IGNORE OLD SWAGGER RESOURCES
    // --------------------------------------------------

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {

        return web -> web.ignoring().requestMatchers(
                "/v2/api-docs",
                "/configuration/ui",
                "/swagger-resources/**",
                "/configuration/security",
                "/swagger-ui.html",
                "/webjars/**"
        );
    }


    // --------------------------------------------------
    // INITIAL DATA
    // --------------------------------------------------

    @Bean
    public CommandLineRunner initData(
            RoleRepository roleRepository,
            UserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {

            // ==========================================
            // CREATE / FIND ROLES
            // ==========================================

            Role userRole = roleRepository
                    .findByRoleName(AppRole.ROLE_USER)
                    .orElseGet(() ->
                            roleRepository.save(
                                    new Role(AppRole.ROLE_USER)
                            )
                    );

            Role sellerRole = roleRepository
                    .findByRoleName(AppRole.ROLE_SELLER)
                    .orElseGet(() ->
                            roleRepository.save(
                                    new Role(AppRole.ROLE_SELLER)
                            )
                    );

            Role adminRole = roleRepository
                    .findByRoleName(AppRole.ROLE_ADMIN)
                    .orElseGet(() ->
                            roleRepository.save(
                                    new Role(AppRole.ROLE_ADMIN)
                            )
                    );


            // ==========================================
            // USER ROLES
            // ==========================================

            Set<Role> userRoles = new HashSet<>();
            userRoles.add(userRole);


            // ==========================================
            // SELLER ROLES
            // ==========================================

            Set<Role> sellerRoles = new HashSet<>();
            sellerRoles.add(sellerRole);


            // ==========================================
            // ADMIN ROLES
            // ==========================================

            Set<Role> adminRoles = new HashSet<>();
            adminRoles.add(userRole);
            adminRoles.add(sellerRole);
            adminRoles.add(adminRole);


            // ==========================================
            // CREATE USER1
            // ==========================================

            User user1;

            if (!userRepository.existsByUserName("user1")) {

                user1 = new User(
                        "user1",
                        "user1@example.com",
                        passwordEncoder.encode("password1")
                );

                user1.setRoles(new HashSet<>(userRoles));

                userRepository.save(user1);

            } else {

                userRepository.findByUserName("user1")
                        .ifPresent(user -> {
                            user.setRoles(new HashSet<>(userRoles));
                            userRepository.save(user);
                        });
            }


            // ==========================================
            // CREATE SELLER1
            // ==========================================

            User seller1;

            if (!userRepository.existsByUserName("seller1")) {

                seller1 = new User(
                        "seller1",
                        "seller1@example.com",
                        passwordEncoder.encode("password2")
                );

                seller1.setRoles(new HashSet<>(sellerRoles));

                userRepository.save(seller1);

            } else {

                userRepository.findByUserName("seller1")
                        .ifPresent(seller -> {
                            seller.setRoles(new HashSet<>(sellerRoles));
                            userRepository.save(seller);
                        });
            }


            // ==========================================
            // CREATE ADMIN
            // ==========================================

            User admin;

            if (!userRepository.existsByUserName("admin")) {

                admin = new User(
                        "admin",
                        "admin@example.com",
                        passwordEncoder.encode("adminPass")
                );

                admin.setRoles(new HashSet<>(adminRoles));

                userRepository.save(admin);

            } else {

                userRepository.findByUserName("admin")
                        .ifPresent(existingAdmin -> {
                            existingAdmin.setRoles(new HashSet<>(adminRoles));
                            userRepository.save(existingAdmin);
                        });
            }
        };
    }
}