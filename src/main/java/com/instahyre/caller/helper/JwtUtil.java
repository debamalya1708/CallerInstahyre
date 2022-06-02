package com.instahyre.caller.helper;

import com.instahyre.caller.model.CallerConstants;
import com.instahyre.caller.model.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtUtil {

    private static final long serialVersionUID = -2550185165626007488L;
    public static final long JWT_TOKEN_VALIDITY = 5 * 60 * 60;

    private String SECRET_KEY = "tosmart@drupsystem";

    public String getUsernameFromToken(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date getExpirationDateFromToken(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return getExpirationDateFromToken(token).before(new Date());
    }

    public String generateToken(User user, UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CallerConstants.contactNo, userDetails.getUsername());
        claims.put(CallerConstants.userId,user != null ? user.getId()+"": "0");
        return createToken(claims, userDetails.getUsername());
    }

    private String createToken(Map<String, Object> claims, String subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, SECRET_KEY).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = getUsernameFromToken(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }

    public Map<String, String> getJwtTokenDetails(HttpServletRequest request){
        String requestHeader = request.getHeader("Authorization");
        if(requestHeader!=null && requestHeader.startsWith("Bearer ")) {
            String jwtToken = requestHeader.substring(7);
            Claims claims = Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(jwtToken).getBody();
            Map<String, String> map = new HashMap<>();
            map.put(CallerConstants.contactNo, claims.get(CallerConstants.contactNo, String.class));
            map.put(CallerConstants.userId, claims.get(CallerConstants.userId, String.class));
            return map;
        }
        return null;
    }
}
