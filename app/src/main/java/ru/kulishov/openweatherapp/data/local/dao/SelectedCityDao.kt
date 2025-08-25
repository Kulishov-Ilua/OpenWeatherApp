package ru.kulishov.openweatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.data.local.data.entity.SelectedCityEntity

@Dao
interface SelectedCityDao {
    @Query("Select * from selectedCities")
    fun getSelectedCities(): Flow<List<SelectedCityEntity>>

    @Insert
    suspend fun insertSelectedCities(city: SelectedCityEntity): Long

    @Delete
    suspend fun deleteSelectedCity(city: SelectedCityEntity)
}