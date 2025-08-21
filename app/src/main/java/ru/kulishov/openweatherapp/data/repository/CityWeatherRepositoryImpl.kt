package ru.kulishov.openweatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kulishov.openweatherapp.data.local.dao.CityWeatherDao
import ru.kulishov.openweatherapp.data.local.mapper.CityWeatherMapper
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository
import javax.inject.Inject

class CityWeatherRepositoryImpl @Inject constructor(
    private val cityWeatherDao: CityWeatherDao
): CityWeatherRepository {
    override fun getCityWeather(): Flow<List<WeatherForecastResponse>> = cityWeatherDao.getCityWeather().map { entities -> entities.map { entitity-> CityWeatherMapper.toDomain(entitity) } }

    override fun getCityWeatherByName(name: String): Flow<List<WeatherForecastResponceWithDateTime>>  =cityWeatherDao.getCityWeatherByName(name).map { entities -> entities.map { entitity-> CityWeatherMapper.toDomainWithTime(entitity) } }
    override suspend fun insertWeather(cityWeatherForecastResponse: WeatherForecastResponse) {
        cityWeatherDao.insertCityWeather(CityWeatherMapper.toEntity(cityWeatherForecastResponse))
    }
    override suspend fun updateWeather(cityWeatherForecastResponse: WeatherForecastResponse) {
        cityWeatherDao.updateCityWeather(CityWeatherMapper.toEntity(cityWeatherForecastResponse))
    }
    override suspend fun deleteWeather(cityWeatherForecastResponse: WeatherForecastResponse) {
        cityWeatherDao.deleteCityWeather(CityWeatherMapper.toEntity(cityWeatherForecastResponse))
    }
}