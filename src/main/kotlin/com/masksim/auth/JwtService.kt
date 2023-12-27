package com.masksim.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import com.masksim.data.model.UserModel
import java.time.LocalDateTime
import java.time.ZoneOffset

class JwtService {

    private val issuer = "maksim-cards-server"
    private val jwtSecret = System.getenv("JWT_SECRET")
    private val algorithm = Algorithm.HMAC256(jwtSecret)

    private val verifier: JWTVerifier = JWT
        .require(algorithm)
        .withIssuer(issuer)
        .build()

    fun generateToken(user: UserModel): String {
        return JWT.create()
            .withSubject("CardAppAuth")
            .withIssuer(issuer)
            .withClaim("login", user.login)
            .withExpiresAt(LocalDateTime.now().plusDays(10).toInstant(ZoneOffset.UTC))
            .sign(algorithm)
    }

    fun getVerifier(): JWTVerifier = verifier

}