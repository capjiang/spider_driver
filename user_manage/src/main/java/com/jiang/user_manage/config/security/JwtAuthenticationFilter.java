package com.jiang.user_manage.config.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jiang.user_manage.entity.User;
import com.jiang.user_manage.entity.UserDTO;
import com.jiang.user_manage.entity.UserVO;
import com.jiang.user_manage.entity.response.ResponseResult;
import com.jiang.user_manage.entity.response.ResponseResultGenerator;
import com.jiang.user_manage.mapper.UserMapper;
import com.jiang.user_manage.util.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 认证服务
 */
@Slf4j
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    @Autowired
    private UserMapper userMapper;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager) {
        setAuthenticationManager(authenticationManager);
        // 登陆接口
        setFilterProcessesUrl("/user/login");
    }

    /**
     * 认证准备方法
     *
     * @param request
     * @param response
     * @return
     * @throws AuthenticationException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserDTO loginUser = new ObjectMapper().readValue(request.getInputStream(), UserDTO.class);
            // 校验
            return getAuthenticationManager().authenticate(
                    new UsernamePasswordAuthenticationToken(
                            loginUser.getUsername(),
                            loginUser.getPassword()
                    )
            );
        } catch (Exception e) {
            // username 不存在
            sendFailResponse(response, "username 不存在");
        }
        return null;
    }

    /**
     * 用户登陆成功后，生成token，并且返回json数据给前端
     *
     * @param request
     * @param response
     * @param chain
     * @param authResult
     * @throws IOException
     * @throws ServletException
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException {
        // 获得UserDetails的信息
        UserDetailsEntity userDetailsEntity = (UserDetailsEntity) authResult.getPrincipal();
        User user = userDetailsEntity.getUser();
        String token = JwtUtils.createJwtToken(user.getUserId(), user.getUsername(), user.getPassword());
        // 保存token到db
        user.setToken(token);
        userMapper.updateById(user);
        // 登陆成功，返回token和基本用户信息
        response.setContentType("application/json;charset=utf-8");
        response.setStatus(HttpServletResponse.SC_OK);
        PrintWriter out = response.getWriter();
        ResponseResult responseResult = ResponseResultGenerator.genSuccessResult(new UserVO(user), "authentication pass!", token);
        out.write(new ObjectMapper().writeValueAsString(responseResult));
        out.flush();
        out.close();
    }

    /**
     * 发生登陆失败响应
     *
     * @param response
     * @param exceptionMsg
     */
    protected void sendFailResponse(HttpServletResponse response, String exceptionMsg) {
        try {
            // 登陆失败
            response.setContentType("application/json;charset=utf-8");
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            PrintWriter out = response.getWriter();
            ResponseResult responseResult = ResponseResultGenerator.genBadRequestResult();
            out.write(new ObjectMapper().writeValueAsString(responseResult));
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.error("attemptAuthentication error", e);
        }
    }
}
