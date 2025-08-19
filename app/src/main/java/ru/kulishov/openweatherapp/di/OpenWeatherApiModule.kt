//package ru.kulishov.openweatherapp.di
//
//import com.google.gson.Gson
//import dagger.Module
//import dagger.Provides
//import dagger.hilt.InstallIn
//import dagger.hilt.components.SingletonComponent
//import okhttp3.OkHttpClient
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import ru.kulishov.openweatherapp.data.remote.OpenWeatherApi
//import javax.inject.Singleton
//
//@Module
//@InstallIn(SingletonComponent::class)
//object OpenWeatherApiModule {
//    @Provides
//    @Singleton
//    fun provideRetrofit(): Retrofit{
//        return Retrofit.Builder()
//            .baseUrl("https://api.openweathermap.org/")
//            .client(OkHttpClient())
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//    }
//
//    @Provides
//    @Singleton
//    fun provideOpenWeatherApi(retrofit: Retrofit): OpenWeatherApi{
//        return retrofit.create(OpenWeatherApi::class.java)
//    }
//}