//package ru.kulishov.openweatherapp.di
//
//import android.content.Context
//import androidx.compose.runtime.rememberCoroutineScope
//import androidx.room.Room
//import androidx.room.RoomDatabase
//import androidx.sqlite.db.SupportSQLiteDatabase
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.android.qualifiers.ApplicationContext
//import dagger.hilt.components.SingletonComponent
//import ru.kulishov.openweatherapp.data.local.dao.CityDao
//import ru.kulishov.openweatherapp.data.local.dao.CityWeatherDao
//import ru.kulishov.openweatherapp.data.local.dao.SelectedCityDao
//import ru.kulishov.openweatherapp.data.local.database.AppDatabase
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object DatabaseModule {
//    @Provides
//    @Singleton
//    fun provideDatabase(
//        @ApplicationContext context: Context
//    ): AppDatabase{
//        return Room.databaseBuilder(
//            context,
//            AppDatabase::class.java,
//            "app_database"
//        )
//            .createFromAsset("databases/cities.db")
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun providesCityWeatherDao(db: AppDatabase): CityWeatherDao{
//        return db.cityWeatherDao()
//    }
//
//    @Provides
//    @Singleton
//    fun providesCityDao(db: AppDatabase): CityDao{
//        return db.cityDao()
//    }
//
//    @Provides
//    @Singleton
//    fun providesSelectedCityDao(db: AppDatabase): SelectedCityDao{
//        return db.selectedCityDao()
//    }
//}