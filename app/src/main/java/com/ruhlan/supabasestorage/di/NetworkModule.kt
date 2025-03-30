package com.ruhlan.supabasestorage.di

import com.ruhlan.supabasestorage.BuildConfig.SUPABASE_KEY
import com.ruhlan.supabasestorage.BuildConfig.SUPABASE_URL
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.annotations.SupabaseInternal
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.realtime.Realtime
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.websocket.WebSockets
import javax.inject.Singleton

/**
 * Created on 30 Mar 2025,15:12
 * @author Ruhlan Usubov
 */
@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @OptIn(SupabaseInternal::class)
    @Provides
    @Singleton
    fun provideSupabaseClient() = createSupabaseClient(
        supabaseKey = SUPABASE_KEY,
        supabaseUrl = SUPABASE_URL
    ) {
        install(Realtime)
        install(Postgrest)
        httpConfig {
            install(WebSockets)
            install(Logging) {
                logger = Logger.DEFAULT
                level = LogLevel.ALL
            }
        }
    }

    @Provides
    @Singleton
    fun provideSupaBaseDatabase(supabaseClient: SupabaseClient) = supabaseClient.postgrest
}