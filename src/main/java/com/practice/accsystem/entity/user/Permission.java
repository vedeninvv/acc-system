package com.practice.accsystem.entity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Разрешения на действия в системе
 */
@RequiredArgsConstructor
public enum Permission {
    readUserAll("user:read:all"),
    readUserSelf("user:read:self"),
    writeUserAll("user:write:all"),
    writeUserSelf("user:read:self");

    private final String permissionStr;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(permissionStr);
    }
}
