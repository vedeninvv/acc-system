package com.practice.accsystem.entity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

/**
 * Разрешения на действия в системе
 */
@RequiredArgsConstructor
public enum Permission {
    testPermission("test-perm");

    private final String permissionStr;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(permissionStr);
    }
}
