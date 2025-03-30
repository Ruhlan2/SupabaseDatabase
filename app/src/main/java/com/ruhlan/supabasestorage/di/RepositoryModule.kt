package com.ruhlan.supabasestorage.di

import com.ruhlan.supabasestorage.data.repository.UserRepository
import com.ruhlan.supabasestorage.data.repository.UserRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Created on 30 Mar 2025,15:29
 * @author Ruhlan Usubov
 */
@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindRepository(userRepositoryImpl: UserRepositoryImpl): UserRepository
}