package ru.kulishov.openweatherapp.data.local.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Fts4

@Fts4(contentEntity = CityEntity::class)
@Entity(tableName = "cities_fts")
data class CityFtsEntity(
    @ColumnInfo(name = "localName") val localName: String
)