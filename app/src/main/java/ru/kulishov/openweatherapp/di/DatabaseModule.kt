package ru.kulishov.openweatherapp.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kulishov.openweatherapp.data.local.AppDatabase
import ru.kulishov.openweatherapp.data.local.dao.CityDao
import ru.kulishov.openweatherapp.data.local.dao.CityWeatherDao
import ru.kulishov.openweatherapp.data.local.dao.SelectedCityDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return AppDatabase.getInstance(context)
    }

    @Provides
    @Singleton
    fun provideCityWeatherDao(db: AppDatabase): CityWeatherDao {
        return db.cityWeatherDao()
    }

    @Provides
    @Singleton
    fun provideCityDao(db: AppDatabase): CityDao {
        return db.cityDao()
    }

    @Provides
    @Singleton
    fun provideSelectedCityDao(db: AppDatabase): SelectedCityDao {
        return db.selectedCityDao()
    }

}
