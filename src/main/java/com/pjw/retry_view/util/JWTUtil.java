package com.pjw.retry_view.util;

import com.pjw.retry_view.dto.UserAuth;
import com.pjw.retry_view.dto.UserInfo;
import com.pjw.retry_view.exception.InvalidTokenException;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import io.micrometer.common.util.StringUtils;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
import java.util.*;

@Component
public class JWTUtil {
    private static SecretKey secretKey;
    private static final long ACCESS_TOKEN_EXPIRED = 1000 * 60 * 60; // 1h
    private static final long REFRESH_TOKEN_EXPIRED = 1000 * 60 * 60 * 24 * 7;
    private static final String USER_INFO_ID= "id";
    private static final String USER_INFO_NAME = "name";
    private static final String USER_INFO_LOGIN_ID = "loginId";
    private static final String USER_INFO_ROLE = "role";
    private static final String BEARER_TYPE = "Bearer";// 7day
    public static final String AUTH_KEY = "Authorization";

    public JWTUtil(@Value("${jwt.key}")String key){
        secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public SecretKey getSecretKey(){
        return secretKey;
    }

    public static String createAccessToken(UserInfo userInfo){
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_INFO_ID, userInfo.getId());
        claims.put(USER_INFO_NAME, userInfo.getName());
        claims.put(USER_INFO_LOGIN_ID, userInfo.getLoginId());
        claims.put(USER_INFO_ROLE, userInfo.getRole().getCode());
        return Jwts.builder()
                .claims(claims)
                .issuer("issuer")
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis+ACCESS_TOKEN_EXPIRED))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public static String createRefreshToken(){
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis+REFRESH_TOKEN_EXPIRED))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public static Claims getClaims(String token)throws InvalidTokenException {
        if(StringUtils.isBlank(token)) throw new InvalidTokenException();
        token = splitToken(token);
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public static UserAuth getUserAuthInJWT(String token){
        Claims claims = getClaims(token);
        String authCode = claims.get(USER_INFO_ROLE,String.class);
        return UserAuth.getValue(authCode);
    }

    public static Long getUserId(){
        HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(RequestContextHolder.getRequestAttributes())).getRequest();
        String jwt = request.getHeader("Authorization");

        if(!isValidateToken(jwt)) return null;

        Object obj = JWTUtil.getClaims(jwt).get(USER_INFO_ID);
        return Objects.nonNull(obj) ? Long.parseLong(obj.toString()) : null;
    }

    public static boolean isValidateToken(String token){
        if(StringUtils.isBlank(token)) return false;
        token = splitToken(token);
        try {
            Jwts.parser().verifyWith(secretKey).build().parseSignedClaims(token);
            return true;
        } catch (SecurityException | MalformedJwtException e) {
            System.out.println("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            System.out.println("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            System.out.println("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            System.out.println("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }

    public static boolean isTokenExpired(String token){
        Claims jwtClaims = getClaims(token);
        return jwtClaims.getExpiration().before(new Date());
    }

    public static String splitToken(String token){
        if(StringUtils.isBlank(token)) return token;

        String result = "";
        if(token.startsWith(BEARER_TYPE)){
            result = token.split(" ")[1];
        }
        return result;
    }
}
