package ru.kulishov.openweatherapp.domain.usecase.weather

import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository
import javax.inject.Inject

class DeleteCityWeatherUseCase @Inject constructor(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(weatherForecastResponse: WeatherForecastResponse) =
        repository.deleteWeather(weatherForecastResponse)
}