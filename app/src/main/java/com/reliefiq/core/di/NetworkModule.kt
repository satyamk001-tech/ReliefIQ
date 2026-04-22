package com.reliefiq.core.di

import com.reliefiq.core.network.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.CertificatePinner
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Provides
    @Singleton
    fun provideOkHttpClient(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        val certificatePinner = CertificatePinner.Builder()
            .add("generativelanguage.googleapis.com", "sha256/XXXX") // Replace with actual pin
            .build()

        return OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            // .certificatePinner(certificatePinner) // Enabled for prod
            .build()
    }

    @Provides
    @Singleton
    @Named("Gemini")
    fun provideGeminiRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://generativelanguage.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("GoogleMaps")
    fun provideGoogleMapsRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://maps.googleapis.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    
    @Provides
    @Singleton
    @Named("Twilio")
    fun provideTwilioRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.twilio.com/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    @Named("Weather")
    fun provideWeatherRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.openweathermap.org/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideGeminiApiService(@Named("Gemini") retrofit: Retrofit): GeminiApiService =
        retrofit.create(GeminiApiService::class.java)

    @Provides
    @Singleton
    fun provideGeminiVisionService(@Named("Gemini") retrofit: Retrofit): GeminiVisionService =
        retrofit.create(GeminiVisionService::class.java)

    @Provides
    @Singleton
    fun provideDirectionsApiService(@Named("GoogleMaps") retrofit: Retrofit): DirectionsApiService =
        retrofit.create(DirectionsApiService::class.java)

    @Provides
    @Singleton
    fun providePlacesApiService(@Named("GoogleMaps") retrofit: Retrofit): PlacesApiService =
        retrofit.create(PlacesApiService::class.java)

    @Provides
    @Singleton
    fun provideTwilioService(@Named("Twilio") retrofit: Retrofit): TwilioService =
        retrofit.create(TwilioService::class.java)

    @Provides
    @Singleton
    fun provideWeatherApiService(@Named("Weather") retrofit: Retrofit): WeatherApiService =
        retrofit.create(WeatherApiService::class.java)
}
