package com.paper.di

import com.paper.domain.repos.IAuthRepository
import com.paper.domain.repos.ICommentsRepository
import com.paper.domain.repos.IPostsRepository
import dagger.Component
import javax.inject.Inject
import javax.inject.Singleton

@Component(
    modules = [
        DataModule::class, DomainModule::class
    ]
)
@Singleton
interface ApplicationComponent {

    fun authRepository(): IAuthRepository
    fun postsRepository(): IPostsRepository
    fun commentsRepository(): ICommentsRepository
}