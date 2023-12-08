package com.budgetmanagement.budgetmanagement.controller.user;

import com.budgetmanagement.budgetmanagement.controller.user.request.UserJoinRequest;
import com.budgetmanagement.budgetmanagement.support.response.ApiResponse;
import com.budgetmanagement.budgetmanagement.domain.user.UserService;
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
    public ApiResponse<Void> join(@RequestBody @Valid UserJoinRequest userJoinRequest) {
        userService.join(userJoinRequest.toTarget());
        return ApiResponse.success();
    }
}
