package com.lq.identity.service.impl;

import com.lq.identity.dto.AuthResponse;
import com.lq.identity.dto.LoginRequest;
import com.lq.identity.dto.RefreshTokenRequest;
import com.lq.identity.dto.RegisterRequest;
import com.lq.identity.entity.RefreshToken;
import com.lq.identity.entity.Role;
import com.lq.identity.entity.User;
import com.lq.identity.exception.DuplicateResourceException;
import com.lq.identity.exception.InvalidCredentialsException;
import com.lq.identity.exception.ResourceNotFoundException;
import com.lq.identity.repository.RefreshTokenRepository;
import com.lq.identity.repository.RoleRepository;
import com.lq.identity.repository.UserRepository;
import com.lq.identity.security.JwtTokenProvider;
import com.lq.identity.service.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RefreshTokenRepository refreshTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthServiceImpl(AuthenticationManager authenticationManager,
                           UserRepository userRepository,
                           RoleRepository roleRepository,
                           RefreshTokenRepository refreshTokenRepository,
                           PasswordEncoder passwordEncoder,
                           JwtTokenProvider jwtTokenProvider) {
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.refreshTokenRepository = refreshTokenRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    @Transactional
    public AuthResponse login(LoginRequest loginRequest) {
        String inputKey = loginRequest.getUsernameOrEmail() != null ? loginRequest.getUsernameOrEmail().trim() : "";
        User user = userRepository.findByUsername(inputKey)
                .orElseGet(() -> userRepository.findByEmail(inputKey.toLowerCase())
                        .orElseThrow(() -> new InvalidCredentialsException("Invalid username/email or password")));

        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(user.getUsername(), loginRequest.getPassword())
            );

            String token = jwtTokenProvider.generateToken(authentication, user.getId());
            String refreshTokenStr = createRefreshToken(user).getToken();

            List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

            return AuthResponse.builder()
                    .accessToken(token)
                    .refreshToken(refreshTokenStr)
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(roles)
                    .build();
        } catch (org.springframework.security.authentication.DisabledException e) {
            throw new InvalidCredentialsException("Your account has been suspended by the administrator.");
        } catch (org.springframework.security.core.AuthenticationException e) {
            System.err.println("AUTHENTICATION FAILED FOR: " + inputKey + " - REASON: " + e.getClass().getName() + ": " + e.getMessage());
            if (e.getMessage() != null && e.getMessage().toLowerCase().contains("disabled")) {
                throw new InvalidCredentialsException("Your account has been suspended by the administrator.");
            }
            throw new InvalidCredentialsException("Invalid username/email or password");
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Authentication processing error: " + e.getMessage(), e);
        }
    }

    @Override
    @Transactional
    public AuthResponse register(RegisterRequest registerRequest) {
        String cleanUsername = registerRequest.getUsername().trim();
        String cleanEmail = registerRequest.getEmail().trim().toLowerCase();

        // 1. Purge/unbind any existing account using this email address so MySQL UNIQUE constraint is NEVER violated
        userRepository.findByEmail(cleanEmail).ifPresent(otherUser -> {
            if (!otherUser.getUsername().equalsIgnoreCase(cleanUsername)) {
                try {
                    refreshTokenRepository.deleteByUser(otherUser);
                    otherUser.getRoles().clear();
                    userRepository.delete(otherUser);
                    userRepository.flush();
                } catch (Exception e) {
                    otherUser.setEmail("archived_" + System.currentTimeMillis() + "_" + otherUser.getId() + "@archived.local");
                    userRepository.save(otherUser);
                    userRepository.flush();
                }
            }
        });

        // 2. If username exists: update password, email & roles cleanly
        Optional<User> existingUserOpt = userRepository.findByUsername(cleanUsername);
        if (existingUserOpt.isPresent()) {
            User existingUser = existingUserOpt.get();
            Set<Role> roles = parseRoles(registerRequest.getRoles());
            existingUser.setEmail(cleanEmail);
            existingUser.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
            existingUser.setRoles(roles);
            existingUser.setEnabled(true);
            userRepository.save(existingUser);
            userRepository.flush();
            return login(new LoginRequest(cleanUsername, registerRequest.getPassword()));
        }

        Set<Role> roles = parseRoles(registerRequest.getRoles());

        User user = User.builder()
                .username(cleanUsername)
                .email(cleanEmail)
                .password(passwordEncoder.encode(registerRequest.getPassword()))
                .roles(roles)
                .enabled(true)
                .build();

        userRepository.save(user);
        userRepository.flush();

        LoginRequest loginRequest = new LoginRequest(cleanUsername, registerRequest.getPassword());
        return login(loginRequest);
    }

    private Set<Role> parseRoles(Collection<String> roleNames) {
        Set<Role> roles = new HashSet<>();
        if (roleNames != null && !roleNames.isEmpty()) {
            for (String roleName : roleNames) {
                String cleanRole = roleName.toUpperCase().replace("ROLE_", "");
                Role role = roleRepository.findByName(cleanRole)
                        .orElseGet(() -> roleRepository.save(Role.builder().name(cleanRole).build()));
                roles.add(role);
            }
        } else {
            Role studentRole = roleRepository.findByName("STUDENT")
                    .orElseGet(() -> roleRepository.save(Role.builder().name("STUDENT").description("Student Role").build()));
            roles.add(studentRole);
        }
        return roles;
    }

    @Override
    @Transactional
    public AuthResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        RefreshToken refreshToken = refreshTokenRepository.findByToken(refreshTokenRequest.getRefreshToken())
                .orElseThrow(() -> new ResourceNotFoundException("Refresh token is not in database!"));

        if (refreshToken.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(refreshToken);
            throw new InvalidCredentialsException("Refresh token was expired. Please make a new signin request");
        }

        User user = refreshToken.getUser();
        Authentication authentication = new UsernamePasswordAuthenticationToken(user.getUsername(), null, Collections.emptyList());
        String token = jwtTokenProvider.generateToken(authentication, user.getId());

        List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());

        return AuthResponse.builder()
                .accessToken(token)
                .refreshToken(refreshToken.getToken())
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .roles(roles)
                .enabled(user.isEnabled())
                .build();
    }

    @Override
    @Transactional
    public void logout(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        refreshTokenRepository.deleteByUser(user);
    }

    private RefreshToken createRefreshToken(User user) {
        RefreshToken refreshToken = refreshTokenRepository.findByUser(user)
                .orElseGet(() -> RefreshToken.builder().user(user).build());

        refreshToken.setToken(UUID.randomUUID().toString());
        refreshToken.setExpiryDate(Instant.now().plusMillis(604800000)); // 7 days

        return refreshTokenRepository.save(refreshToken);
    }

    @Override
    @Transactional(readOnly = true)
    public List<AuthResponse> getAllUsers() {
        return userRepository.findAll().stream().map(user -> {
            List<String> roles = user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
            return AuthResponse.builder()
                    .id(user.getId())
                    .username(user.getUsername())
                    .email(user.getEmail())
                    .roles(roles)
                    .enabled(user.isEnabled())
                    .build();
        }).collect(Collectors.toList());
    }

    @Override
    @Transactional
    public AuthResponse updateUserStatus(Long userId, boolean enabled) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        user.setEnabled(enabled);
        User saved = userRepository.save(user);
        List<String> roles = saved.getRoles().stream().map(Role::getName).collect(Collectors.toList());
        return AuthResponse.builder()
                .id(saved.getId())
                .username(saved.getUsername())
                .email(saved.getEmail())
                .roles(roles)
                .enabled(saved.isEnabled())
                .build();
    }

    @Override
    @Transactional
    public void deleteUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + userId));
        refreshTokenRepository.deleteByUser(user);
        user.getRoles().clear();
        userRepository.delete(user);
        userRepository.flush();
    }
}
