package com.paper

object Config {
    object JwtConfig {
        val secret = "secret"
        val issuer = "http://0.0.0.0:8080/"
        val audience = "http://0.0.0.0:8080/"
        val realm = "all"
        val tokenLifetimeMillis = 600000
    }
}