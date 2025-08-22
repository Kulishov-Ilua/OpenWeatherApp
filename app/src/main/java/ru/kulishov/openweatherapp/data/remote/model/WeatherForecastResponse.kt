package ru.kulishov.openweatherapp.data.remote.model

data class WeatherForecastResponse(
    val cod: String,
    val message: Int,
    val cnt: Int,
    val list: List<Forecast>,
    val city: City
)