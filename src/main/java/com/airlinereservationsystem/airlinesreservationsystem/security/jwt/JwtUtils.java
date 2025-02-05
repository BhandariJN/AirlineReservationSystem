//package com.airlinereservationsystem.airlinesreservationsystem.security.jwt;
//
//import io.jsonwebtoken.*;
//import io.jsonwebtoken.io.Decoders;
//import io.jsonwebtoken.security.Keys;
//import io.jsonwebtoken.security.SignatureException;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.security.core.Authentication;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.stereotype.Component;
//
//import java.security.Key;
//import java.util.Date;
//import java.util.List;
//
//@Component
//public class JwtUtils {
//
//
//
//
//        @Value("${jwt.secret}")
//        private String jwtSecret;
//        @Value("${jwt.expiration.time}")
//        private int expirationTime;
//
//
//        public String generateTokenForUser(Authentication authentication){
//            AirlineUser principal = (AirlineUser) authentication.getPrincipal();
//            List<String> roles = principal.getAuthorities().stream()
//                    .map(GrantedAuthority::getAuthority)
//                    .toList();
//            return Jwts.builder()
//                    .setSubject(principal.getUsername())
//                    .claim("id", principal.getId())
//                    .claim("roles", roles)
//                    .setIssuedAt(new Date())
//                    .setExpiration(new Date((new Date()).getTime() + expirationTime))
//                    .signWith(key(), SignatureAlgorithm.HS512)
//                    .compact();
//        }
//
//        private Key key() {
//            return Keys.hmacShaKeyFor(Decoders.BASE64.decode(jwtSecret));
//        }
//
//
//        public String getUserNameFromToken(String token){
//            return Jwts.parserBuilder()
//                    .setSigningKey(key())
//                    .build()
//                    .parseClaimsJws(token)
//                    .getBody()
//                    .getSubject();
//        }
//
//        public boolean validateToken(String jwtToken){
//            try {
//                Jwts.parserBuilder()
//                        .setSigningKey(key())
//                        .build()
//                        .parseClaimsJws(jwtToken);
//                return true;
//            } catch (ExpiredJwtException | IllegalArgumentException | UnsupportedJwtException | MalformedJwtException |
//                     SignatureException e) {
//                throw new JwtException(e.getMessage());
//            }
//
//        }
//    }
//
//
