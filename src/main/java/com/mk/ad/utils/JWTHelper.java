package com.mk.ad.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JWTHelper {

    private static Logger logger = LoggerFactory.getLogger(JWTHelper.class);
    private static final String SECRET = "tz";


    public static String vd(String token) {
        String result = null;
        JWTVerifier verify = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt = null;
        try {
            jwt = verify.verify(token);
            result = jwt.getClaim("iss").asString();
            logger .info(result);
        } catch (Exception e) {
            System.out.println("登录凭证已过期，请重新登录");
        }
        return result;
    }

    /**
     * 获取用户token
     *
     * @param subject 主题
     * @param info    用户信息（默认可以传用户编号）
     * @return 用户token
     */
    public static String getToken(String subject, String info) {
        String result = null;
        Date iatDate = new Date();
        Map<String, Object> map = new HashMap<>(2);
        map.put("typ", "JWT");
        //声明加密的算法，通常直接使用HMAC SHA256
        map.put("alg", "HS256");
        //plyload 载荷，可以理解为承载的物品
        Calendar nowTime = Calendar.getInstance();
        //设置过期时间 -1小时后过期
        nowTime.add(Calendar.HOUR,2);
        //得到时间
        Date expiresDate = nowTime.getTime();
        try {
            Algorithm algorithm = Algorithm.HMAC256(SECRET);
            result = JWT.create()
                    .withHeader(map)
                    .withClaim("iss", info) // 载荷 payload
                    .withClaim("aud", subject)
                    .withExpiresAt(expiresDate)
                    .withIssuedAt(iatDate)
                    .sign(algorithm);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            return null;
        } catch (JWTCreationException e) {
            e.printStackTrace();
            return null;
        }

        return result;
    }


}
