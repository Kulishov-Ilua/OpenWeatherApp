package ru.kulishov.openweatherapp.domain.usecase.weather

import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository

class UpdateCityWeatherUseCase(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(weatherForecastResponse: WeatherForecastResponse) = repository.updateWeather(weatherForecastResponse)
}