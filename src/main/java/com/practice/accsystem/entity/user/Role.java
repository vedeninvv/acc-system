package com.practice.accsystem.entity.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static com.practice.accsystem.entity.user.Permission.*;

/**
 * Роли пользователя
 */
@RequiredArgsConstructor
public enum Role {
    ADMIN("ADMIN", Arrays.asList(
            readUserAll,
            writeUserAll,
            readCounterparty,
            writeCounterparty,
            readContractAll,
            writeContractAll,
            writeCounterpartyContract,
            readCounterpartyContract,
            writeContractStage,
            readContractStage,
            report)
    ),
    USER("USER", Arrays.asList(
            readUserSelf,
            writeUserSelf,
            readCounterparty,
            writeCounterparty,
            readContractSelf,
            writeContractSelf,
            writeCounterpartyContract,
            readCounterpartyContract,
            writeContractStage,
            readContractStage,
            report)
    );

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
