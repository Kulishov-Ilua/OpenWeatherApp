package ru.kulishov.openweatherapp.domain.usecase.weather

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository
import javax.inject.Inject

class GetCityWeatherUseCase @Inject constructor(
    private val repository: CityWeatherRepository
) {
    suspend operator fun invoke(): Flow<List<WeatherForecastResponse>> = repository.getCityWeather()
}