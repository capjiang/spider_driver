package com.jiang.user_manage.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;

import java.util.Calendar;
import java.util.List;

public class JwtUtils {

    public static final String DEFAULT_ALGORITHM_KEY = "spider_driver";
    public static final String TOKEN_HEADER_KEY = "Bearer";

    public static String createJwtToken(Integer userId, String username, String algorithmKey) {
        // token24小时过期
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.HOUR, 24);
        return JWT.create()
                .withAudience(Integer.toString(userId), username)
                .withExpiresAt(calendar.getTime())
                .sign(Algorithm.HMAC256(algorithmKey));
    }

    /**
     * 验证失败时会报错
     *
     * @param token
     * @param algorithmKey
     */
    public static void verifyToken(String token, String algorithmKey) {
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(algorithmKey)).build();
        verifier.verify(token);
    }

    /**
     * 从token中解析出userId
     *
     * @param token
     * @return
     */
    public static String getUserId(String token) {
        List<String> audiences = JWT.decode(token).getAudience();
        return audiences == null ? null : audiences.get(0);
    }

    /**
     * 从token中解析出username
     *
     * @param token
     * @return
     */
    public static String getUserName(String token) {
        List<String> audiences = JWT.decode(token).getAudience();
        return audiences == null ? null : audiences.get(1);
    }


}
