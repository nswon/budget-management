package com.budgetmanagement.budgetmanagement.support.response;

import com.budgetmanagement.budgetmanagement.support.error.ErrorMessage;
import com.budgetmanagement.budgetmanagement.support.error.ErrorType;

public record ApiResponse<S>(ResultType resultType, S data, ErrorMessage error) {

    public static ApiResponse<Void> success() {
        return new ApiResponse<>(ResultType.SUCCESS, null, null);
    }

    public static <S> ApiResponse<S> success(S data) {
        return new ApiResponse<>(ResultType.SUCCESS, data, null);
    }

    public static ApiResponse<?> error(ErrorType errorType) {
        return new ApiResponse<>(ResultType.ERROR, null, new ErrorMessage(errorType));
    }

    public static ApiResponse<?> error(ErrorType errorType, Object errorData) {
        return new ApiResponse<>(ResultType.ERROR, errorData, new ErrorMessage(errorType, errorData));
    }
}
