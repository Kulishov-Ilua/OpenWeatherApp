package ru.kulishov.openweatherapp.data.remote.model

import ru.kulishov.openweatherapp.data.remote.model.Clouds

data class Forecast(
    val dt: Long,
    val main: MainForecast,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
    val visibility: Int,
    val pop: Double,
    val sys: Sys,
    val dt_txt: String
)