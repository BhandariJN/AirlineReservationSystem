//package com.airlinereservationsystem.airlinesreservationsystem.security.jwt;
//
//import io.jsonwebtoken.JwtException;
//import jakarta.servlet.FilterChain;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//import lombok.NonNull;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.security.core.context.SecurityContextHolder;
//import org.springframework.security.web.AuthenticationEntryPoint;
//import org.springframework.stereotype.Component;
//import org.springframework.util.StringUtils;
//import org.springframework.web.filter.OncePerRequestFilter;
//
//import java.io.IOException;
//
//@RequiredArgsConstructor
//@Component
//public class AuthTokenFilter extends OncePerRequestFilter {
//    private final JwtUtils jwtUtils;
//    private final AirlineUserService userService;
//
//
//    @Override
//    protected void doFilterInternal(@NonNull HttpServletRequest request,
//                                    @NonNull HttpServletResponse response,
//                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
//
//        try {
//            String jwt = parseJwtToken(request);
//
//            if(StringUtils.hasText(jwt)&& jwtUtils.validateToken(jwt)){
//                String email = jwtUtils.getUserNameFromToken(jwt);
//                AirlineUser userDetails = (AirlineUser) userService.loadUserByUsername(email);
//
//
//                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
//                        userDetails, null, userDetails.getAuthorities());
//                SecurityContextHolder.getContext().setAuthentication(authentication);
//
//            }
//        } catch (JwtException e) {
//            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
//            response.getWriter().write(e.getMessage()+"Invalid JWT token");
//            return;
//        }
//    }
//
//    private String parseJwtToken(HttpServletRequest request) {
//        String headerAuth = request.getHeader("Authorization");
//
//        if (StringUtils.hasText(headerAuth) && headerAuth.startsWith("Bearer ")) {
//            return headerAuth.substring(7);
//        }
//        return null;
//    }
//
//
//
//
//}
