package tk.slaaavyn.slavikhomebackend.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.slaaavyn.slavikhomebackend.config.EndpointConstants;
import tk.slaaavyn.slavikhomebackend.dto.auth.AuthRequestDto;
import tk.slaaavyn.slavikhomebackend.dto.auth.AuthResponseDto;
import tk.slaaavyn.slavikhomebackend.dto.auth.RefreshTokenRequestDto;
import tk.slaaavyn.slavikhomebackend.dto.user.UserResponseDto;
import tk.slaaavyn.slavikhomebackend.exception.ApiRequestException;
import tk.slaaavyn.slavikhomebackend.model.RefreshToken;
import tk.slaaavyn.slavikhomebackend.model.User;
import tk.slaaavyn.slavikhomebackend.security.SecurityConstants;
import tk.slaaavyn.slavikhomebackend.security.jwt.JwtTokenProvider;
import tk.slaaavyn.slavikhomebackend.service.RefreshTokenService;
import tk.slaaavyn.slavikhomebackend.service.UserService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.Date;

@RestController
@RequestMapping(value = EndpointConstants.AUTH_ENDPOINT)
public class AuthController {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final AuthenticationManager authenticationManager;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthController(UserService userService, RefreshTokenService refreshTokenService,
                          AuthenticationManager authenticationManager, JwtTokenProvider jwtTokenProvider) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @PostMapping
    public ResponseEntity<AuthResponseDto> login(@RequestBody @Valid AuthRequestDto requestDto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(requestDto.getUsername(), requestDto.getPassword()));

        User user = userService.getByUsername(requestDto.getUsername());

        return ResponseEntity.ok(generateTokenResponse(user));
    }

    @PostMapping(value = "/refresh-token")
    public ResponseEntity<AuthResponseDto> refreshToken(@RequestBody @Valid RefreshTokenRequestDto requestDto) {
        User user = userService.getByUsername(requestDto.getUsername());

        if (!refreshTokenService.isTokenValid(requestDto.fromDto())) {
            throw new ApiRequestException("refresh token is not valid");
        }

        return ResponseEntity.ok(generateTokenResponse(user));
    }

    private AuthResponseDto generateTokenResponse(User user) {
        Date tokenExpired = new Date(new Date().getTime() + SecurityConstants.TOKEN_VALIDITY_TIME);

        String token = SecurityConstants.TOKEN_PREFIX +
                jwtTokenProvider.createToken(user.getUsername(), Collections.singletonList(user.getRole().name()));

        RefreshToken refreshToken = refreshTokenService.create(user.getUsername());

        return AuthResponseDto.toDto(UserResponseDto.toDto(user), token, tokenExpired, refreshToken);
    }

}
