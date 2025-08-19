package ru.kulishov.openweatherapp.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.data.local.entity.CityEntity

@Dao
interface CityDao {
    @Query("""
        SELECT * FROM cities 
        WHERE id IN (
            SELECT rowid FROM cities_fts 
            WHERE cities_fts MATCH :query || '*'
        )
        LIMIT 20
    """)
    fun searchCities(query: String): Flow<List<CityEntity>>


    @Query("SELECT * FROM cities  LIMIT 20")
    suspend fun searchCitiesSimple(): List<CityEntity>
}