package io.github.theisson.ecommerce.controllers;

import java.net.URI;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import io.github.theisson.ecommerce.dto.LoginRequestDTO;
import io.github.theisson.ecommerce.dto.RefreshTokenRequestDTO;
import io.github.theisson.ecommerce.dto.TokenResponseDTO;
import io.github.theisson.ecommerce.dto.UserRequestDTO;
import io.github.theisson.ecommerce.dto.UserResponseDTO;
import io.github.theisson.ecommerce.services.application.LoginUser;
import io.github.theisson.ecommerce.services.application.LogoutUser;
import io.github.theisson.ecommerce.services.application.RefreshTokens;
import io.github.theisson.ecommerce.services.application.RegisterUser;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthController {
    
    private final RegisterUser registerUser;
    private final LoginUser loginUser;
    private final RefreshTokens refreshTokens;
    private final LogoutUser logoutUser;

    public AuthController(RegisterUser registerUser, LoginUser loginUser, RefreshTokens refreshTokens, LogoutUser logoutUser) {
        this.registerUser = registerUser;
        this.loginUser = loginUser;
        this.refreshTokens = refreshTokens;
        this.logoutUser = logoutUser;
    }
    
    @PostMapping("/register")
    public ResponseEntity<UserResponseDTO> register(@Valid @RequestBody UserRequestDTO dto) {
        UserResponseDTO response = registerUser.execute(dto);
        
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
                .replacePath("/users/{id}")
                .buildAndExpand(response.id())
                .toUri();
                
        return ResponseEntity.created(uri).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponseDTO> login(@Valid @RequestBody LoginRequestDTO dto) {
        return ResponseEntity.ok(loginUser.execute(dto));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponseDTO> refresh(@Valid @RequestBody RefreshTokenRequestDTO dto) {
        return ResponseEntity.ok(refreshTokens.execute(dto));
    }

    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@Valid @RequestBody RefreshTokenRequestDTO dto, @AuthenticationPrincipal Jwt jwt) {
        Long userId = Long.parseLong(jwt.getSubject());
        logoutUser.execute(dto, userId);
        return ResponseEntity.noContent().build();
    }
}
