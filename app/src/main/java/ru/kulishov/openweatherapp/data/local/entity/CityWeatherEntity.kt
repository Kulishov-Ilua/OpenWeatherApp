package ru.kulishov.openweatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity (tableName = "cityWeather")
data class CityWeatherEntity(
    @PrimaryKey(autoGenerate = false) val cityId:Int,
    val cityName:String,
    val sunrise:Long,
    val sunset:Long,
    val forecastTime:Long,
    val forecast:String
)
