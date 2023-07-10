package com.fooddelivery.backend.Config;


import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;

import com.fooddelivery.backend.Models.Users;
import com.fooddelivery.backend.Security.SecurityConstant;

import com.fooddelivery.backend.Service.UserService;

@Service
public class SocketService {
    @Autowired
    UserService userService;
   

    public Authentication authenticateMessageFromSocket( String token1) {
        String token = token1.replace(SecurityConstant.authorization, "");
        DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC512(SecurityConstant.private_key)).build().verify(token);
        String username = decodedJWT.getSubject();
        List<String> claims = decodedJWT.getClaim("authorities").asList(String.class);
        List<SimpleGrantedAuthority> authorities = claims.stream().map(clai -> new SimpleGrantedAuthority(clai)).collect(Collectors.toList());
        Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);
        SecurityContextHolder.getContext().setAuthentication(authentication);
        // UsernamePasswordAuthenticationToken authenticationToken =  new UsernamePasswordAuthenticationToken(username, null, authorities);
        return authentication;
    }
 

    public Authentication authenticateMessageToken(String token) {
        Authentication authenticationToken = authenticateMessageFromSocket( token);

        return authenticationToken;
    }
}
