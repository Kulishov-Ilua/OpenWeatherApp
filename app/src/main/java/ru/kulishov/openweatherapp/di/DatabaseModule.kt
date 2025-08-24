package ru.kulishov.openweatherapp.di

import android.content.Context
import android.location.LocationManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.kulishov.openweatherapp.data.local.dao.CityDao
import ru.kulishov.openweatherapp.data.local.dao.CityWeatherDao
import ru.kulishov.openweatherapp.data.local.dao.SelectedCityDao
import ru.kulishov.openweatherapp.data.local.database.AppDatabase
import ru.kulishov.openweatherapp.data.repository.CityRepositoryImpl
import ru.kulishov.openweatherapp.data.repository.CityWeatherRepositoryImpl
import ru.kulishov.openweatherapp.data.repository.SelectedCityRepositoryImpl
import ru.kulishov.openweatherapp.domain.repository.CityRepository
import ru.kulishov.openweatherapp.domain.repository.CityWeatherRepository
import ru.kulishov.openweatherapp.domain.repository.SelectedCityRepository
import ru.kulishov.openweatherapp.domain.usecase.cities.DeleteSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.FindCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.GetSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.InsertSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.GetCityWeatherByNameUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.InsertCityWeatherUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.UpdateCityWeatherUseCase
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

    @Provides
    @Singleton
    fun provideCityRepository(
        cityDao: CityDao
    ): CityRepository {
        return CityRepositoryImpl(cityDao)
    }

    @Provides
    @Singleton
    fun provideSelectedCityRepository(
        selectedCityDao: SelectedCityDao
    ): SelectedCityRepository = SelectedCityRepositoryImpl(selectedCityDao)

    @Provides
    @Singleton
    fun provideCityWeatherRepository(
        cityWeatherDao: CityWeatherDao
    ): CityWeatherRepository = CityWeatherRepositoryImpl(cityWeatherDao)

    @Provides
    @Singleton
    fun provideFindCityUseCase(
        cityRepository: CityRepository
    ): FindCityUseCase = FindCityUseCase(cityRepository)

    @Provides
    @Singleton
    fun provideGetCityWeatherByNameUseCase(
        cityWeatherRepository: CityWeatherRepository
    ): GetCityWeatherByNameUseCase = GetCityWeatherByNameUseCase(cityWeatherRepository)

    @Provides
    @Singleton
    fun provideUpdateCityWeatherUseCase(
        cityWeatherRepository: CityWeatherRepository
    ): UpdateCityWeatherUseCase = UpdateCityWeatherUseCase(cityWeatherRepository)

    @Provides
    @Singleton
    fun provideInsertCityWeatherUseCase(
        cityWeatherRepository: CityWeatherRepository
    ): InsertCityWeatherUseCase = InsertCityWeatherUseCase(cityWeatherRepository)

    @Provides
    @Singleton
    fun provideGetSelectedCityUseCase(
        selectedCityRepository: SelectedCityRepository
    ): GetSelectedCityUseCase = GetSelectedCityUseCase(selectedCityRepository)

    @Provides
    @Singleton
    fun provideInsertSelectedCityUseCase(
        selectedCityRepository: SelectedCityRepository
    ): InsertSelectedCityUseCase = InsertSelectedCityUseCase(selectedCityRepository)

    @Provides
    @Singleton
    fun provideDeleteSelectedCityUseCase(
        selectedCityRepository: SelectedCityRepository
    ): DeleteSelectedCityUseCase = DeleteSelectedCityUseCase(selectedCityRepository)

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideLocationManager(@ApplicationContext context: Context): LocationManager {
        return context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }
}
