package ru.kulishov.openweatherapp.domain.usecase.weather

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository

class GetCityWeatherUseCase(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(): Flow<List<WeatherForecastResponse>> = repository.getCityWeather()
}