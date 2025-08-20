package ru.kulishov.openweatherapp.data.remote.api

import android.content.ContentValues.TAG
import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import ru.kulishov.openweatherapp.BuildConfig
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse

fun cityRequest(retrofit: Retrofit, city: String, onSuccess: (WeatherForecastResponse)->Unit, onFailure: (String)->Unit){
    val openWeatherApi: OpenWeatherApi = retrofit.create(OpenWeatherApi::class.java)
    openWeatherApi.getWeather(city, BuildConfig.OW_API_KEY).enqueue(object:
        Callback<WeatherForecastResponse> {
        override fun onResponse(
            call: Call<WeatherForecastResponse>,
            response: Response<WeatherForecastResponse>
        ) {
            if(response.isSuccessful){
                val weather = response.body()
                println("data: $weather")
                if(weather !=null){
                    Log.d(TAG, "Responce body: $weather")
                    onSuccess(weather)
                }else{
                    Log.e(TAG, "Failed ${response.message()}")
                    onFailure("Request failed: ${response.message()}")
                }
            }else{
                println("Error: ${response.code()} - ${response.errorBody()?.string()}")
            }
        }

        override fun onFailure(call: Call<WeatherForecastResponse>, t: Throwable) {
            Log.e(TAG, "Network error ${t.message}")
            onFailure("Network error ${t.message}")
        }
    })
}