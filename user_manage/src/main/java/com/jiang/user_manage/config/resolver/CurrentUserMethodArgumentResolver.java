package com.jiang.user_manage.config.resolver;

import com.jiang.user_manage.annotation.CurrentUser;
import com.jiang.user_manage.entity.User;
import org.springframework.core.MethodParameter;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

public class CurrentUserMethodArgumentResolver implements HandlerMethodArgumentResolver {

    public static final String CURRENT_USER = "currentUser";

    @Override
    public boolean supportsParameter(MethodParameter methodParameter) {
        // 是user类型并且有currentUser注解才处理
        return methodParameter.getParameterType().isAssignableFrom(User.class)
                && methodParameter.hasParameterAnnotation(CurrentUser.class);
    }

    @Override
    public Object resolveArgument(MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest, WebDataBinderFactory webDataBinderFactory) throws Exception {
        User user = (User) nativeWebRequest.getAttribute(CURRENT_USER, RequestAttributes.SCOPE_REQUEST);
        if (user != null) {
            return user;
        }
        throw new MissingServletRequestPartException(CURRENT_USER);
    }
}
