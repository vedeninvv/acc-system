package com.practice.accsystem.controller;

import com.practice.accsystem.dto.user.UserGetDto;
import com.practice.accsystem.dto.user.UserPostDto;
import com.practice.accsystem.entity.user.Role;
import com.practice.accsystem.mapper.UserMapper;
import com.practice.accsystem.security.UserDetailsImpl;
import com.practice.accsystem.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/current")
    public UserGetDto currentUser(@Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userMapper.toDto(
                userService.findUserById(userDetails.getId())
        );
    }

    @PreAuthorize("hasAuthority('user:read:all') or (hasAuthority('user:read:self') and #userDetails.id == #userId)")
    @GetMapping("/{userId}")
    public UserGetDto findUserById(@PathVariable Long userId,
                                   @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userMapper.toDto(
                userService.findUserById(userId)
        );
    }

    @PreAuthorize("hasAuthority('user:read:all')")
    @GetMapping
    public Page<UserGetDto> findAllUsers(@RequestParam(required = false) Role role,
                                         @RequestParam(required = false) String searchStr,
                                         @ParameterObject @PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable pageable) {
        return userService.findAllUsers(role, searchStr, pageable).map(userMapper::toDto);
    }

    @PreAuthorize("hasAuthority('user:write:all') or (hasAuthority('user:write:self') and #userDetails.id == #userId)")
    @PutMapping("/{userId}")
    public UserGetDto updateUser(@PathVariable Long userId,
                                 @Valid @RequestBody UserPostDto userPostDto,
                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userMapper.toDto(
                userService.updateUser(
                        userService.findUserById(userId),
                        userMapper.toEntity(userPostDto)
                )
        );
    }

    @PreAuthorize("hasAuthority('user:write:all') or (hasAuthority('user:write:self') and #userDetails.id == #userId)")
    @DeleteMapping("/{userId}")
    public UserGetDto deleteUser(@PathVariable Long userId,
                                 @Parameter(hidden = true) @AuthenticationPrincipal UserDetailsImpl userDetails) {
        return userMapper.toDto(
                userService.deleteUser(
                        userService.findUserById(userId)
                )
        );
    }
}
