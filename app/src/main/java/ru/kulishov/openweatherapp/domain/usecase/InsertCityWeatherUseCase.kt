package ru.kulishov.openweatherapp.domain.usecase

import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository

class InsertCityWeatherUseCase(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(weatherForecastResponse: WeatherForecastResponse) = repository.insertWeather(weatherForecastResponse)
}