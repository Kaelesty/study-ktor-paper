package com.paper

object Config {
    object JwtConfig {
        val secret = "secret"
        val issuer = "http://0.0.0.0:8080/"
        val audience = "http://0.0.0.0:8080/"
        val realm = "all"
        val tokenLifetimeMillis = 600000
    }
    object DatabaseConfig {
        val url = "jdbc:postgresql://localhost:5432/"
        val user = "postgres"
        val driver = "org.postgresql.Driver"
        val password = "188348"
    }
}