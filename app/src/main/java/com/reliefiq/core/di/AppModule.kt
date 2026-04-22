package com.reliefiq.core.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    // EncryptedSharedPreferences is now initialized lazily inside SecureStorage
    // to avoid blocking the main thread during Hilt initialization.
}
