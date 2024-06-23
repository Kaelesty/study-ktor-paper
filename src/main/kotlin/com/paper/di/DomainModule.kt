package com.paper.di

import com.paper.data.database.repoimpls.AuthRepository
import com.paper.data.database.repoimpls.CommentsRepository
import com.paper.data.database.repoimpls.PostsRepository
import com.paper.domain.repos.IAuthRepository
import com.paper.domain.repos.ICommentsRepository
import com.paper.domain.repos.IPostsRepository
import dagger.Binds
import dagger.Module

@Module
interface DomainModule {

    @Binds
    fun bindIAuthRepository(impl: AuthRepository): IAuthRepository

    @Binds
    fun bindICommentsRepository(impl: CommentsRepository): ICommentsRepository

    @Binds
    fun bindIPostsRepository(impl: PostsRepository): IPostsRepository
}