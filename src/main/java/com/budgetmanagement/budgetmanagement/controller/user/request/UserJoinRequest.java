package com.budgetmanagement.budgetmanagement.controller.user.request;

import com.budgetmanagement.budgetmanagement.domain.user.UserTarget;
import jakarta.validation.constraints.Pattern;

public record UserJoinRequest(
        String account,

        @Pattern(regexp = "^(?=(.*\\d.*))(?=(.*[a-zA-Z].*))(?=(.*[!@#$%^&*()].*)).{10,}$",
                message = "비밀번호는 숫자, 문자, 특수문자 중 2가지 이상 포함하고, 최소 10자 이상이어야 합니다.")
        String password
) {
        public UserTarget toTarget() {
                return new UserTarget(account, password);
        }
}
