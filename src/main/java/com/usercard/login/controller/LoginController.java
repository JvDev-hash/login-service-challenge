package com.usercard.login.controller;

import com.usercard.login.DTO.LoginDTO;
import com.usercard.login.DTO.LoginResponseDTO;
import com.usercard.login.service.JwtTokenService;
import com.usercard.login.service.LoginService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import jakarta.servlet.http.Cookie;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/")
@Tag(name = "Login")
public class LoginController {
    private final LoginService loginService;
    private final JwtTokenService jwtTokenService;

    Logger logger = LoggerFactory.getLogger(LoginController.class);

    public LoginController(LoginService loginService, JwtTokenService jwtTokenService){
        this.loginService = loginService;
        this.jwtTokenService = jwtTokenService;
    }

    @Operation(summary = "Faz o Login de acordo com o email e senha")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Login efetuado",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = LoginResponseDTO.class)) }),
            @ApiResponse(responseCode = "404", description = "Login não efetuado: Usuário Inexistente",
                    content = @Content),
            @ApiResponse(responseCode = "500", description = "Login não efetuado: Erro interno do servidor",
                    content = @Content) })
    @PostMapping("login")
    public ResponseEntity<?> login(@RequestBody LoginDTO loginDTO, HttpServletResponse response){
        try{
            var usuario = loginService.getByEmailAndPassword(loginDTO.getEmail(), loginDTO.getSenha());

            if (usuario == null) {
                return new ResponseEntity<>("Usuário ou senha errados!", HttpStatus.NOT_FOUND);
            }

            var tokenJWT = jwtTokenService.generateToken(usuario);

            Cookie cookie = new Cookie("token", tokenJWT);
            cookie.setHttpOnly(true);
            cookie.setSecure(false);
            cookie.setPath("/");
            cookie.setMaxAge(60 * 60 * 24);

            response.addCookie(cookie);

            return new ResponseEntity<>(new LoginResponseDTO(usuario.getNome(), usuario.getPermissoes().toString()), HttpStatus.OK);

        } catch (Exception e) {
            logger.error(e.getMessage());
            if(e.getClass().getName().equals("jakarta.persistence.EntityNotFoundException")) {
                return new ResponseEntity<>(e.getMessage().getBytes(StandardCharsets.UTF_8), HttpStatus.NOT_FOUND);
            } else {
                return new ResponseEntity<>("Internal Server Error: " + e.getClass().getName(), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
    }

}
