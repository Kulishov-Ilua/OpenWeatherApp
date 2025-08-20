package ru.kulishov.openweatherapp.data.remote.model

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double?
)