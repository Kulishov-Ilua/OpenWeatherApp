package ru.kulishov.openweatherapp.data.local.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import kotlinx.coroutines.Dispatchers
import ru.kulishov.openweatherapp.data.local.dao.CityDao
import ru.kulishov.openweatherapp.data.local.dao.CityWeatherDao
import ru.kulishov.openweatherapp.data.local.dao.SelectedCityDao
import ru.kulishov.openweatherapp.data.local.entity.CityEntity
import ru.kulishov.openweatherapp.data.local.entity.CityFtsEntity
import ru.kulishov.openweatherapp.data.local.entity.CityWeatherEntity
import ru.kulishov.openweatherapp.data.local.entity.SelectedCityEntity

@Database(
    entities = [CityWeatherEntity::class,
        CityEntity::class,
        CityFtsEntity::class,
        SelectedCityEntity::class], version = 1
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun cityWeatherDao(): CityWeatherDao
    abstract fun cityDao(): CityDao
    abstract fun selectedCityDao(): SelectedCityDao


}

fun getRoomDatabase(
    builder: RoomDatabase.Builder<AppDatabase>
): AppDatabase {

    return builder
        .fallbackToDestructiveMigrationOnDowngrade()
        .setQueryCoroutineContext(Dispatchers.IO)
        .createFromAsset("databases/cities.db")
        .build()
}

fun getDatabaseBuilder(ctx: Context): RoomDatabase.Builder<AppDatabase> {
    val appContext = ctx.applicationContext
    val dbFile = appContext.getDatabasePath("my_room.db")

    val builder = Room.databaseBuilder<AppDatabase>(
        context = appContext,
        name = dbFile.absolutePath
    )
    return builder
}

