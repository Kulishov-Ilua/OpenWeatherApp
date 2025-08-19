package ru.kulishov.openweatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.data.local.entity.CityEntity
import ru.kulishov.openweatherapp.data.local.entity.SelectedCityEntity

@Dao
interface SelectedCityDao {
    @Query("Select * from selectedCities")
    fun getSelectedCities(): Flow<List<SelectedCityEntity>>

    @Insert
    fun insertSelectedCities(city: SelectedCityEntity)

    @Delete
    fun deleteSelectedCity(city: SelectedCityEntity)
}