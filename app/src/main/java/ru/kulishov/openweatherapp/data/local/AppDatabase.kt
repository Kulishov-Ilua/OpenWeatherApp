package ru.kulishov.openweatherapp.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import kotlinx.coroutines.Dispatchers
import ru.kulishov.openweatherapp.data.local.dao.CityDao
import ru.kulishov.openweatherapp.data.local.dao.CityWeatherDao
import ru.kulishov.openweatherapp.data.local.dao.SelectedCityDao
import ru.kulishov.openweatherapp.data.local.data.entity.CityEntity
import ru.kulishov.openweatherapp.data.local.data.entity.CityFtsEntity
import ru.kulishov.openweatherapp.data.local.data.entity.CityWeatherEntity
import ru.kulishov.openweatherapp.data.local.data.entity.SelectedCityEntity

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

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null
        private val lock = Any()

        fun getInstance(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(lock) {
                INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return getRoomDatabase(getDatabaseBuilder(context))
        }
    }
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

