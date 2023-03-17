package com.practice.accsystem.controller;

import com.practice.accsystem.dto.auth.AuthMessageResponse;
import com.practice.accsystem.dto.auth.AuthRefreshRequest;
import com.practice.accsystem.dto.auth.AuthRequest;
import com.practice.accsystem.dto.auth.AuthResponse;
import com.practice.accsystem.dto.user.UserGetDto;
import com.practice.accsystem.dto.user.UserPostDto;
import com.practice.accsystem.entity.user.RefreshToken;
import com.practice.accsystem.exception.TokenRefreshException;
import com.practice.accsystem.mapper.UserMapper;
import com.practice.accsystem.security.UserDetailsImpl;
import com.practice.accsystem.security.jwt.JwtUtils;
import com.practice.accsystem.security.jwt.RefreshTokenService;
import com.practice.accsystem.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Objects;

import static com.practice.accsystem.config.OpenApiConfiguration.SECURITY_CONFIG_NAME;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;
    private final RefreshTokenService refreshTokenService;
    private final UserService userService;
    private final UserMapper userMapper;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtils jwtUtils,
                          RefreshTokenService refreshTokenService,
                          UserService userService,
                          UserMapper userMapper) {
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
        this.refreshTokenService = refreshTokenService;
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager
                .authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        String accessToken = jwtUtils.generateJwt(userDetails);
        String refreshToken = refreshTokenService.createRefreshToken(userDetails.getId()).getToken();

        return ResponseEntity.ok()
                .body(new AuthResponse(accessToken, refreshToken));
    }

    @PostMapping("/signup")
    public UserGetDto createUser(@Valid @RequestBody UserPostDto userPostDto) {
        return userMapper.toDto(
                userService.createUser(userMapper.toEntity(userPostDto))
        );
    }

    @SecurityRequirement(name = SECURITY_CONFIG_NAME)
    @PostMapping("/signout")
    public ResponseEntity<?> logoutUser() {
        Object principle = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (!Objects.equals(principle.toString(), "anonymousUser")) {
            Long userId = ((UserDetailsImpl) principle).getId();
            refreshTokenService.deleteByUserId(userId);
        }

        return ResponseEntity.ok()
                .body(new AuthMessageResponse("You've been signed out!"));
    }

    @PostMapping("/refreshtoken")
    public ResponseEntity<?> refreshToken(@RequestBody AuthRefreshRequest request) {
        String refreshToken = request.getRefreshToken();

        if ((refreshToken != null) && (refreshToken.length() > 0)) {
            return refreshTokenService.findByToken(refreshToken)
                    .map(refreshTokenService::verifyExpiration)
                    .map(RefreshToken::getUser)
                    .map(user -> {
                        String accessToken = jwtUtils.generateJwt(user);

                        return ResponseEntity.ok()
                                .body(AuthResponse.builder()
                                        .accessToken(accessToken)
                                        .build());
                    })
                    .orElseThrow(() -> new TokenRefreshException(refreshToken, "Refresh token is not in database!"));
        }

        return ResponseEntity.badRequest().body(new AuthMessageResponse("Refresh Token is empty!"));
    }
}