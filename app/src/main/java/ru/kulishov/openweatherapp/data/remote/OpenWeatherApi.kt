package ru.kulishov.openweatherapp.data.remote

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse

interface OpenWeatherApi {
    @GET("data/2.5/forecast")
    fun getWeather(
        @Query("q") city:String,
        @Query("appid") apiKey:String,
        @Query("units") units: String = "metric"
    ): Call<WeatherForecastResponse>
}