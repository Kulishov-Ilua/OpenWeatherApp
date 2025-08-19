package ru.kulishov.openweatherapp.data.local.mapper

import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse

object WeatherForecastMapper {
    fun toForecastWithoutDate(forecast: WeatherForecastResponceWithDateTime): WeatherForecastResponse =
        WeatherForecastResponse(
            cod = forecast.cod,
            message = forecast.message,
            cnt = forecast.cnt,
            list = forecast.list,
            city = forecast.city
        )
    fun toForecastWithDate(forecast: WeatherForecastResponse): WeatherForecastResponceWithDateTime =
        WeatherForecastResponceWithDateTime(
            cod = forecast.cod,
            message = forecast.message,
            cnt = forecast.cnt,
            list = forecast.list,
            city = forecast.city,
            update = System.currentTimeMillis()
        )
}