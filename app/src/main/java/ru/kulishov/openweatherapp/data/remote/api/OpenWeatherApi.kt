package ru.kulishov.openweatherapp.data.remote.api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse

interface OpenWeatherApi {
    @GET("data/2.5/forecast")
    fun getWeather(
        @Query("q") city:String,
        @Query("appid") apiKey:String,
        @Query("units") units: String = "metric"
    ): Call<WeatherForecastResponse>

    @GET("data/2.5/forecast")
    fun getWeatherByGeo(
        @Query("lat") lat:Double,
        @Query("lon") lon:Double,
        @Query("appid") apiKey:String,
        @Query("units") units: String = "metric"
    ): Call<WeatherForecastResponse>
}