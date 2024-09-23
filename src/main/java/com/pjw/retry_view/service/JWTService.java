package com.pjw.retry_view.service;

import com.pjw.retry_view.response.JWToken;
import com.pjw.retry_view.dto.UserDTO;
import com.pjw.retry_view.dto.UserInfo;
import com.pjw.retry_view.entity.User;
import com.pjw.retry_view.exception.InvalidTokenException;
import com.pjw.retry_view.exception.UserNotFoundException;
import com.pjw.retry_view.repository.UserRepository;
import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SecurityException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.crypto.SecretKey;
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

    private final UserRepository userRepository;

    public JWTService(@Value("${jwt.key}")String key, UserRepository userRepository){
        this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(key));
        this.userRepository = userRepository;
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

    @Transactional
    public JWToken renewAccessToken(String refreshToken) throws InvalidTokenException {
        boolean isValidate = this.validateToken(refreshToken);
        boolean isExpired = this.isTokenExpired(refreshToken);
        JWToken token = new JWToken();
        UserDTO user = userRepository.findByRefreshToken(refreshToken).map(User::toDTO).orElseThrow(UserNotFoundException::new);
        UserInfo userInfo = new UserInfo(user.getName(), user.getLoginId());

        if(isValidate && !isExpired){ // refreshToken이 유효한 경우
            token.setAccessToken(createAccessToken(userInfo));
            return token;
        }else if(isValidate && isExpired){// refreshToken이 만료된 경우 access,refreshToken 둘 다 생성
            refreshToken = createRefreshToken();
            token.setAccessToken(createAccessToken(userInfo));
            token.setRefreshToken(refreshToken);
            user.setRefreshToken(refreshToken);
            userRepository.save(user.toEntity());
            return token;
        }else{
            throw new InvalidTokenException();
        }
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

    public boolean isTokenExpired(String token){
        Claims jwtClaims = getClaims(token);
        return jwtClaims.getExpiration().before(new Date());
    }

    private String tokenSplit(String token){
        if(token.startsWith(BEARER_TYPE)){
            return token.split(" ")[1];
        } else{
            return token;
        }
    }
}
