package ru.kulishov.openweatherapp.domain.model

data class Wind(
    val speed: Double,
    val deg: Int,
    val gust: Double?
)
