package com.practice.accsystem.entity.user;

import com.sun.xml.internal.ws.policy.privateutil.PolicyUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.accsystem.entity.user.Permission.testPermission;

/**
 * Роли пользователя
 */
@RequiredArgsConstructor
public enum Role {
    ADMIN("ADMIN", Collections.singletonList(testPermission)),
    USER("USER", Collections.singletonList(testPermission));

    private final String roleStr;
    private final List<Permission> permissions;

    public List<GrantedAuthority> getAuthorities() {
        List<GrantedAuthority> authorities = permissions.stream().
                map(Permission::getAuthority)
                .collect(Collectors.toList());
        authorities.add(new SimpleGrantedAuthority("ROLE_" + roleStr));
        return authorities;
    }
}
