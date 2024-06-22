package com.paper.di

import com.paper.data.database.AuthRepository
import com.paper.domain.repos.IAuthRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindIAuthRepository(impl: AuthRepository): IAuthRepository
}