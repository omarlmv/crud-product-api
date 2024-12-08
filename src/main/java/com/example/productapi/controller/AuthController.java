package com.example.productapi.controller;

import com.example.productapi.api.AuthenticationApi;
import com.example.productapi.config.JwtUtil;
import com.example.productapi.model.AuthRequest;
import com.example.productapi.model.AuthResponse;
import com.example.productapi.model.ErrorResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.OffsetDateTime;
import java.util.Date;

@RestController
public class AuthController implements AuthenticationApi {

    @Autowired
    private ReactiveAuthenticationManager reactiveAuthenticationManager;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private ReactiveUserDetailsService userDetailsService;

    @Override
    public Mono<ResponseEntity<AuthResponse>> authenticateUser(Mono<AuthRequest> authRequestMono, ServerWebExchange exchange) {
        return authRequestMono.flatMap(authRequest -> {
            UsernamePasswordAuthenticationToken authToken =
                    new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword());

            return reactiveAuthenticationManager.authenticate(authToken)
                    .flatMap(authentication -> userDetailsService.findByUsername(authRequest.getUsername())
                            .flatMap(userDetails -> {
                                String jwt = jwtUtil.generateToken(userDetails.getUsername());
                                OffsetDateTime expirationDate = jwtUtil.extractExpiration(jwt);
                                AuthResponse authResponse = new AuthResponse(jwt, expirationDate);
                                return Mono.just(ResponseEntity.ok(authResponse));
                            }))
                    .onErrorResume(e -> Mono.just(ResponseEntity.status(401).body(new AuthResponse())));
        });
    }
}