package ru.kulishov.openweatherapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse

interface CityWeatherRepository {
    suspend fun getCityWeather(): Flow<List<WeatherForecastResponse>>
    suspend fun insertWeather(cityWeatherForecastResponse: WeatherForecastResponse)
    suspend fun updateWeather(cityWeatherForecastResponse: WeatherForecastResponse)
    suspend fun deleteWeather(cityWeatherForecastResponse: WeatherForecastResponse)
}