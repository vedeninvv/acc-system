package com.practice.accsystem.security.jwt;

import com.practice.accsystem.entity.user.AppUserEntity;
import com.practice.accsystem.entity.user.RefreshToken;
import com.practice.accsystem.exception.NotFoundEntityException;
import com.practice.accsystem.exception.TokenRefreshException;
import com.practice.accsystem.repository.RefreshTokenRepository;
import com.practice.accsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.Optional;

@Service
public class RefreshTokenService {
    @Value("${accsystem.jwtRefreshExpirationMs}")
    private Long refreshTokenDurationMs;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UserRepository userRepository;
    private final JwtUtils jwtUtils;

    public RefreshTokenService(RefreshTokenRepository refreshTokenRepository, UserRepository userRepository, JwtUtils jwtUtils) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.userRepository = userRepository;
        this.jwtUtils = jwtUtils;
    }

    public Optional<RefreshToken> findByToken(String token) {
        return refreshTokenRepository.findByToken(token);
    }

    public RefreshToken createRefreshToken(Long userId) {
        RefreshToken refreshToken = new RefreshToken();

        AppUserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new NotFoundEntityException(
                        String.format("User not found when try to create refreshToken for user by id %d", userId)));
        refreshToken.setUser(user);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenDurationMs));
        refreshToken.setToken(jwtUtils.generateTokenFromUsername(user.getUsername(), refreshTokenDurationMs));

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken verifyExpiration(RefreshToken token) {
        if (token.getExpiryDate().compareTo(Instant.now()) < 0) {
            refreshTokenRepository.delete(token);
            throw new TokenRefreshException(token.getToken(), "Refresh token was expired. Please make a new signin request");
        }

        return token;
    }

    @Transactional
    public void deleteByUserId(Long userId) {
        refreshTokenRepository.deleteByUserId(userId);
    }
}
