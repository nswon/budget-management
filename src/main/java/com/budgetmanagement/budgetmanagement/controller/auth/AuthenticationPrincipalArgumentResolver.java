package com.budgetmanagement.budgetmanagement.controller.auth;

import com.budgetmanagement.budgetmanagement.domain.auth.AuthInfo;
import com.budgetmanagement.budgetmanagement.domain.auth.AuthTokenExtractor;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AuthenticationPrincipalArgumentResolver implements HandlerMethodArgumentResolver {
    private final AuthTokenExtractor authTokenExtractor;

    public AuthenticationPrincipalArgumentResolver(AuthTokenExtractor authTokenExtractor) {
        this.authTokenExtractor = authTokenExtractor;
    }

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(AuthenticationPrincipal.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
                                  NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
        String accessToken = AuthorizationExtractor.extract(request);
        AuthInfo info = authTokenExtractor.extract(accessToken);
        return new LoginUser(info.id());
    }
}
