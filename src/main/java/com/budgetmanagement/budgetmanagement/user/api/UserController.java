package com.budgetmanagement.budgetmanagement.user.api;

import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
import com.budgetmanagement.budgetmanagement.user.domain.UserService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/join")
    public ApiResponse<?> join(@RequestBody @Valid UserJoinRequest userJoinRequest) {
        userService.join(userJoinRequest.toUserRequest());
        return ApiResponse.success();
    }
}
