package com.jiang.user_manage.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.user_manage.config.resolver.CurrentUserMethodArgumentResolver;
import com.jiang.user_manage.entity.User;
import com.jiang.user_manage.entity.response.ResponseResult;
import com.jiang.user_manage.entity.response.ResponseResultGenerator;
import com.jiang.user_manage.exception.UnauthenticatedException;
import com.jiang.user_manage.util.JwtUtils;
import com.jiang.user_manage.util.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 授权服务
 */
@Slf4j
public class JwtAuthorizationFilter extends BasicAuthenticationFilter {

    @Autowired
    private UserDetailsService userDetailsService;

    // 无需授权路径
    private final String[] DEFAULT_PASS_PATH = {
            "/user/login", "/user/info", "/user/resetPW", "/user/updateAvatar",
            "/mail/**"
    };

    public JwtAuthorizationFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        try {
            // 直接通过
            if (StringUtils.anyEquals(request.getServletPath(), DEFAULT_PASS_PATH)) {
                chain.doFilter(request, response);
                return;
            }
            // 请求头中是否包含Authorization
            String token = request.getHeader(JwtUtils.TOKEN_HEADER_KEY);
            // Authorization中是否包含Bearer
            if (StringUtils.isEmpty(token)) {
                responseForUnauthorized(response, "header no has Bearer");
                return;
            }
            // 获取权限，失败会抛出
            UsernamePasswordAuthenticationToken authentication = getAuthentication(request, token);
            // 将权限角色写入SecurityContextHold中,供后序使用
            SecurityContextHolder.getContext().setAuthentication(authentication);
            chain.doFilter(request, response);
        } catch (Exception e) {
            responseForUnauthorized(response, e.getMessage());
            log.error("doFilterInternal", e);
        }
    }

    /**
     * 未登陆 or token验证失败
     *
     * @param response
     * @param errMsg
     */
    private void responseForUnauthorized(HttpServletResponse response, String errMsg) {

        try {
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            PrintWriter out = response.getWriter();
            ResponseResult responseResult = ResponseResultGenerator.genBadRequestResult();
            out.write(new ObjectMapper().writeValueAsString(responseResult));
            out.flush();
            out.close();
        } catch (IOException e) {
            log.error("responseForUnauthorized", e);
        }
    }

    /**
     * 通过token获取用户信息
     *
     * @param request
     * @param token
     * @return
     */
    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request, String token) throws UnauthenticatedException {
        try {
            // 通过token解析出用户信息
            String username = JwtUtils.getUserName(token);
            // 获取用户信息
            UserDetailsEntity userDetailsEntity = (UserDetailsEntity) userDetailsService.loadUserByUsername(username);
            User user = userDetailsEntity.getUser();
            if (token.equals(userDetailsEntity.getToken())) {
                JwtUtils.verifyToken(token, user.getPassword());
            } else {
                // 默认Token验证
                JwtUtils.verifyToken(token, JwtUtils.DEFAULT_ALGORITHM_KEY);
            }
            request.setAttribute(CurrentUserMethodArgumentResolver.CURRENT_USER, user);
            return new UsernamePasswordAuthenticationToken(user, token, userDetailsEntity.getAuthorities());
        } catch (Exception e) {
            throw new UnauthenticatedException("Unauthorized Invalid Token.");
        }
    }
}
