package com.practice.accsystem.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;

import java.util.Date;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginHistory {
    @Id
    private String id;
    private String username;
    private Long userId;
    private Date loginDate;

    public LoginHistory(String username, Long userId, Date loginDate) {
        this.username = username;
        this.userId = userId;
        this.loginDate = loginDate;
    }
}
