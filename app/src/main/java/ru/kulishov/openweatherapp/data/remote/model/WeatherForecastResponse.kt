package ru.kulishov.openweatherapp.data.remote.model

import ru.kulishov.openweatherapp.data.remote.model.City
import ru.kulishov.openweatherapp.data.remote.model.Forecast

data class WeatherForecastResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
)