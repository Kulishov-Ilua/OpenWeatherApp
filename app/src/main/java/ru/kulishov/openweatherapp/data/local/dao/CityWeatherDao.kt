package ru.kulishov.openweatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.data.local.entity.CityWeatherEntity

@Dao
interface CityWeatherDao {
    @Query("SELECT * FROM cityWeather")
    fun getCityWeather(): Flow<List<CityWeatherEntity>>

    @Query("Select * from cityWeather where cityName = :name")
    fun getCityWeatherByName(name:String): Flow<List<CityWeatherEntity>>

    @Insert
    suspend fun insertCityWeather(forecast: CityWeatherEntity)

    @Delete
    suspend fun deleteCityWeather(cityWeather: CityWeatherEntity)

    @Update
    suspend fun updateCityWeather(cityWeather: CityWeatherEntity)
}