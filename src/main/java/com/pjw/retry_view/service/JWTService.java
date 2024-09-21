package com.pjw.retry_view.service;

import com.pjw.retry_view.dto.UserInfo;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service
public class JWTService {

    private final SecretKey secretKey;
    //private static final String AUTH_KEY = "auth";
    private static final String BEARER_TYPE = "Bearer";
    private static final long ACCESS_TOKEN_EXPIRED = 1000 * 60 * 60; // 1h
    private static final long REFRESH_TOKEN_EXPIRED = 1000 * 60 * 60 * 24 * 7; // 7day

    private static final String USER_INFO_NAME = "name";
    private static final String USER_INFO_LOGIN_ID = "loginId";

    public JWTService(@Value("${jwt.key}")String key){
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
    }

    public String createAccessToken(UserInfo userInfo){
        long currentTimeMillis = System.currentTimeMillis();
        Map<String, Object> claims = new HashMap<>();
        claims.put(USER_INFO_NAME, userInfo.getName());
        claims.put(USER_INFO_LOGIN_ID, userInfo.getLoginId());
        return Jwts.builder()
                .claims(claims)
                .issuer("issuer")
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis+ACCESS_TOKEN_EXPIRED))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public String createRefreshToken(){
        long currentTimeMillis = System.currentTimeMillis();
        return Jwts.builder()
                .issuedAt(new Date(currentTimeMillis))
                .expiration(new Date(currentTimeMillis+REFRESH_TOKEN_EXPIRED))
                .signWith(secretKey, Jwts.SIG.HS512)
                .compact();
    }

    public Claims getClaims(String token){
        token = tokenSplit(token);
        return Jwts.parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }

    public boolean validateToken(String token){
        if(token == null) return false;
        token = tokenSplit(token);
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

    private String tokenSplit(String token){
        if(token.startsWith(BEARER_TYPE)){
            return token.split(" ")[1];
        } else{
            return token;
        }
    }
}
