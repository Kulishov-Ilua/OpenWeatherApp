package ru.kulishov.openweatherapp.domain.usecase.weather

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository

class GetCityWeatherByNameUseCase(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(name:String): Flow<List<WeatherForecastResponceWithDateTime>> = repository.getCityWeatherByName(name)
}