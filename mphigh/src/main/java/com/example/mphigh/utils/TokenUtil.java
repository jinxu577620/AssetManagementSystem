package com.example.mphigh.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.example.mphigh.exception.WoodSongException;
import com.example.mphigh.result.CodeMsg;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    /** token秘钥，请勿泄露，请勿随便修改 backups:d16deac17fb31d63 */
    public static final String SECRET = "d16deac17fb31d63";
    // 签名是有谁生成
    static final String ISSUSER = "jinxu";
    // 签名的主题
    static final String SUBJECT = "this is zichanguanlixitong token";
    // 签名的观众
    static final String AUDIENCE = "APP";

    /** token 过期时间: 10天 */
    public static final int calendarField = Calendar.DATE;
    public static final int calendarInterval = 2;

    /**
     * JWT构成: 头部header, 载荷payload, 签证signature
     * @param id
     * @return
     */
    public static String getToken(String id) {

        // header Map
        Map<String, Object> map = new HashMap<>();
        map.put("alg", "HS256");    //声明加密的算法
        map.put("typ", "JWT");   //声明类型

        // 签发时间
        Date iatDate = new Date();

        // 过期时间
        Calendar nowTime = Calendar.getInstance();
        nowTime.add(calendarField, calendarInterval);
        Date expiresDate = nowTime.getTime();

        String token = null;
        token = JWT.create().withHeader(map)                // 头部
                .withClaim("userId", id)              // 将 user id 保存到 token 里面
                .withIssuedAt(iatDate)                      // 签发时间
                .withExpiresAt(expiresDate)                 // 过期时间
                .sign(Algorithm.HMAC256(SECRET));           // 以 SECRET 作为 token 的密钥
        return token;
    }

    /**
     * 解密Token
     * @param token
     * @return
     */
    public static Map<String, Claim> verifyToken(String token) {
        DecodedJWT jwt = null;
        try {
            JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
            jwt = verifier.verify(token);
        } catch (TokenExpiredException e) {
            // e.printStackTrace();
            // token 过期
            throw new WoodSongException(CodeMsg.TOKEN_EXPIRED);
        } catch (Exception e) {
             e.printStackTrace();
            // token 校验失败, 抛出Token验证非法异常
            throw new WoodSongException(CodeMsg.NO_AUTHORITY);
        }
        return jwt.getClaims();
    }
}
