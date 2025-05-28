package com.pm.authservice.controller;

import com.pm.authservice.dto.LoginRequestDTO;
import com.pm.authservice.dto.LoginResponseDTO;
import com.pm.authservice.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
public class AuthController {
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);
    private final AuthService authService;

    public  AuthController(AuthService authService) {
        this.authService = authService;
    }
    @PostMapping("/login")
    @Operation(summary = "Generate auth token for login ")
    ResponseEntity<LoginResponseDTO> login (@RequestBody LoginRequestDTO loginCredential){
        Optional<String> optionalToken = authService.authenticate(loginCredential);

        if(optionalToken.isEmpty()){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
        return ResponseEntity.ok(new LoginResponseDTO(optionalToken.get()));
    }

    @GetMapping("/validate")
    @Operation(summary ="Validate auth token")
    public ResponseEntity<Void> validateToken(@RequestHeader("Authorization") String authHeader){

        if(authHeader == null || ! authHeader.startsWith("Bearer ")){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

        else{
            return authService.validateToken(authHeader.substring(7))
                    ? ResponseEntity.ok().build() :
                    ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }


}
