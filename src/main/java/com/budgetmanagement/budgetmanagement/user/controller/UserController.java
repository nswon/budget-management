package com.budgetmanagement.budgetmanagement.user.controller;

import com.budgetmanagement.budgetmanagement.user.application.UserService;
import com.budgetmanagement.budgetmanagement.user.dto.request.UserJoinRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/join")
    public ResponseEntity<Void> join(@RequestBody @Valid UserJoinRequest userJoinRequest) {
        userService.join(userJoinRequest);
        return ResponseEntity.noContent().build();
    }
}
