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
    writeUserSelf("user:read:self"),
    readCounterparty("counterparty:read:all"),
    writeCounterparty("counterparty:write:all"),
    readContractSelf("contract:read:self"),
    writeContractSelf("contract:write:self"),
    readContractAll("contract:read:all"),
    writeContractAll("contract:write:all"),
    writeCounterpartyContract("counterpartyContract:write"),
    readCounterpartyContract("counterpartyContract:read");

    private final String permissionStr;

    public GrantedAuthority getAuthority() {
        return new SimpleGrantedAuthority(permissionStr);
    }
}
