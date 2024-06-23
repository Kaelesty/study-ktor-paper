package com.paper.di

import com.paper.Config
import com.paper.data.database.repoimpls.JwtEnvironment
import dagger.Module
import dagger.Provides
import org.jetbrains.exposed.sql.Database
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideDatabase() = with(Config.DatabaseConfig) {
        Database.connect(
            url = url,
            user = user,
            driver = driver,
            password = password
        )
    }

    @Provides
    @Singleton
    fun provideJwtEnvironment() = with(Config.JwtConfig) {
        JwtEnvironment(
            secret, issuer, audience, realm, tokenLifetimeMillis
        )
    }
}