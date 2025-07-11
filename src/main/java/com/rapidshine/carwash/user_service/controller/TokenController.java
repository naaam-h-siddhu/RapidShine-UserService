package com.rapidshine.carwash.user_service.controller;

import com.rapidshine.carwash.user_service.dto.TokenRequest;
import com.rapidshine.carwash.user_service.dto.TokenResponse;
import com.rapidshine.carwash.user_service.service.TokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/oauth")
@RequiredArgsConstructor
public class TokenController {

    private final TokenService tokenService;
    @PostMapping("/token")
    public ResponseEntity<?> generateToken(@RequestBody TokenRequest tokenRequest){
        if(!"client_credentials".equals(tokenRequest.getGrant_type())){
            return ResponseEntity.badRequest().body("Unsupported grant type");
        }
        String token = tokenService.generateM2MToken(tokenRequest.getClient_id(),tokenRequest.getClient_secret());
        TokenResponse response = new TokenResponse(token,"Bearer",3600);
        return ResponseEntity.ok(response);

    }
}
