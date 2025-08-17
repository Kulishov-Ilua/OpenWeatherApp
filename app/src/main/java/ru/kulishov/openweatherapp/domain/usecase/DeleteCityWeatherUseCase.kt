package ru.kulishov.openweatherapp.domain.usecase

import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository

class DeleteCityWeatherUseCase(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(weatherForecastResponse: WeatherForecastResponse) = repository.deleteWeather(weatherForecastResponse)
}