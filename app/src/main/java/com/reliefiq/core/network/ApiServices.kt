package com.reliefiq.core.network

import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

interface GeminiApiService {
    @POST("v1beta/models/gemini-pro:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiTextRequest
    ): GeminiResponse
}

interface GeminiVisionService {
    @POST("v1beta/models/gemini-pro-vision:generateContent")
    suspend fun generateContent(
        @Query("key") apiKey: String,
        @Body request: GeminiVisionRequest
    ): GeminiResponse
}

interface DirectionsApiService {
    @POST("maps/api/directions/json")
    suspend fun getDirections(
        @Query("origin") origin: String,
        @Query("destination") destination: String,
        @Query("waypoints") waypoints: String,
        @Query("mode") mode: String = "driving",
        @Query("key") apiKey: String
    ): DirectionsResponse
}

interface PlacesApiService {
    @POST("maps/api/place/autocomplete/json")
    suspend fun getAutocomplete(
        @Query("input") input: String,
        @Query("types") types: String = "geocode",
        @Query("key") apiKey: String
    ): PlacesAutocompleteResponse

    @POST("maps/api/place/details/json")
    suspend fun getPlaceDetails(
        @Query("place_id") placeId: String,
        @Query("fields") fields: String = "geometry,name,formatted_address",
        @Query("key") apiKey: String
    ): PlaceDetailsResponse

    @POST("maps/api/place/nearbysearch/json")
    suspend fun getNearbySearch(
        @Query("location") location: String,
        @Query("radius") radius: Int,
        @Query("type") type: String,
        @Query("key") apiKey: String
    ): NearbySearchResponse
}

interface TwilioService {
    @retrofit2.http.FormUrlEncoded
    @POST("2010-04-01/Accounts/{accountSid}/Messages.json")
    suspend fun sendSms(
        @retrofit2.http.Path("accountSid") accountSid: String,
        @retrofit2.http.Header("Authorization") authHeader: String,
        @retrofit2.http.Field("From") from: String,
        @retrofit2.http.Field("To") to: String,
        @retrofit2.http.Field("Body") body: String
    ): TwilioResponse
}

interface WeatherApiService {
    @retrofit2.http.GET("data/2.5/weather")
    suspend fun getCurrentWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("units") units: String = "metric"
    ): WeatherResponse

    @retrofit2.http.GET("data/3.0/onecall")
    suspend fun getOneCallWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String,
        @Query("exclude") exclude: String = "minutely,hourly",
        @Query("units") units: String = "metric"
    ): OneCallWeatherResponse
}

// Temporary empty DTOs to make it compile
class GeminiTextRequest
class GeminiResponse
class GeminiVisionRequest
class DirectionsResponse
class PlacesAutocompleteResponse
class PlaceDetailsResponse
class NearbySearchResponse
class TwilioResponse
class WeatherResponse
class OneCallWeatherResponse
