package ru.kulishov.openweatherapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "cities")
data class CityEntity(
    @PrimaryKey val id:Int,
    val localName:String,
    val enName:String
)