package ru.kulishov.openweatherapp.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import ru.kulishov.openweatherapp.data.local.dao.CityWeatherDao
import ru.kulishov.openweatherapp.data.local.entity.CityWeatherEntity

@Database(entities = [CityWeatherEntity::class], version = 1)
abstract class AppDatabase: RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao
}