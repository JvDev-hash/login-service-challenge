package com.usercard.login.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.usercard.login.model.Usuario;
import org.springframework.stereotype.Service;
import io.github.cdimascio.dotenv.Dotenv;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

@Service
public class JwtTokenService {

    private static final Dotenv dotenv = Dotenv.load();

    private static final String SECRET_KEY = dotenv.get("SECRET_KEY");

    private static final String ISSUER = dotenv.get("ISSUER");

    public String generateToken(Usuario usuario) {
        try {
            // Define o algoritmo HMAC SHA256 para criar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.create()
                    .withIssuer(ISSUER) // Define o emissor do token
                    .withSubject("User Permissions") // Define o assunto do token (neste caso, o nome de usuário)
                    .withIssuedAt(new Date())
                    .withExpiresAt(expirationDate())
                    .withClaim("nome", usuario.getNome())
                    .withClaim("permissao", usuario.getPermissoes().toString())
                    .sign(algorithm); // Assina o token usando o algoritmo especificado
        } catch (JWTCreationException exception){
            throw new JWTCreationException("Erro ao gerar token.", exception);
        }
    }

    public String getSubjectFromToken(String token) {
        try {
            // Define o algoritmo HMAC SHA256 para verificar a assinatura do token passando a chave secreta definida
            Algorithm algorithm = Algorithm.HMAC256(SECRET_KEY);
            return JWT.require(algorithm)
                    .withIssuer(ISSUER) // Define o emissor do token
                    .build()
                    .verify(token) // Verifica a validade do token
                    .getSubject(); // Obtém o assunto (neste caso, o nome de usuário) do token
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Token inválido ou expirado.");
        }
    }

    private Instant expirationDate(){
        LocalDate today = LocalDate.now();
        LocalDate expirationDate = today.plusDays(1);
        return expirationDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
    }

}
