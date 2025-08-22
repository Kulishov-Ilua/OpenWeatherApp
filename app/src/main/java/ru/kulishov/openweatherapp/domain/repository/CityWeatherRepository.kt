package ru.kulishov.openweatherapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse

interface CityWeatherRepository {
    fun getCityWeather(): Flow<List<WeatherForecastResponse>>
    fun getCityWeatherByName(name: String): Flow<List<WeatherForecastResponceWithDateTime>>
    suspend fun insertWeather(cityWeatherForecastResponse: WeatherForecastResponse)
    suspend fun updateWeather(cityWeatherForecastResponse: WeatherForecastResponse)
    suspend fun deleteWeather(cityWeatherForecastResponse: WeatherForecastResponse)
}