package ru.kulishov.openweatherapp

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.gson.Gson

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import ru.kulishov.openweatherapp.data.local.entity.CityWeatherEntity
import ru.kulishov.openweatherapp.data.local.mapper.CityWeatherMapper
import ru.kulishov.openweatherapp.domain.model.City
import ru.kulishov.openweatherapp.domain.model.Clouds
import ru.kulishov.openweatherapp.domain.model.Coord
import ru.kulishov.openweatherapp.domain.model.Forecast
import ru.kulishov.openweatherapp.domain.model.MainForecast
import ru.kulishov.openweatherapp.domain.model.Sys
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.model.Wind

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class ExampleInstrumentedTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("ru.kulishov.openweatherapp", appContext.packageName)
    }

    @Test
    fun checking_the_mapper_for_conversion_from_entity_CityWeather__to_domain(){
        val entity = CityWeatherEntity(
            cityId = 0,
            cityName = "Test City",
            sunrise = 1234,
            sunset = 1234,
            forecast = "123#36.6#36.6#36.6#36.6#43#80#45#45.512312#23#101.1#2021-01-01 15:00]["+
                    "123#36.6#36.6#36.6#36.6#43#80#45#45.512312#23#101.1#2021-01-01 15:00]["
        )

        val goodDomain = WeatherForecastResponse(
            cod="200 db_data",
            message = 0,
            cnt = 40,
            list = listOf(
                Forecast(
                    dt=123,
                    main = MainForecast(
                        temp = 36.6,
                        feels_like = 36.6,
                        temp_min = 36.6,
                        temp_max = 36.6,
                        pressure = 43,
                        sea_level = null,
                        grnd_level = null,
                        humidity = 80,
                        temp_kf = 0.0
                    ),
                    emptyList(),
                    clouds = Clouds( all = 45),
                    wind = Wind(
                        speed = 45.512312,
                        deg=0,
                        gust = null
                    ),
                    visibility = 23,
                    pop = 101.1,
                    sys = Sys(""),
                    dt_txt = "2021-01-01 15:00"
                ),
                Forecast(
                    dt=123,
                    main = MainForecast(
                        temp = 36.6,
                        feels_like = 36.6,
                        temp_min = 36.6,
                        temp_max = 36.6,
                        pressure = 43,
                        sea_level = null,
                        grnd_level = null,
                        humidity = 80,
                        temp_kf = 0.0
                    ),
                    emptyList(),
                    clouds = Clouds( all = 45),
                    wind = Wind(
                        speed = 45.512312,
                        deg=0,
                        gust = null
                    ),
                    visibility = 23,
                    pop = 101.1,
                    sys = Sys(""),
                    dt_txt = "2021-01-01 15:00"
                ),

            ),
            city= City(
                id= 0,
                name = "Test City",
                coord = Coord(0.0,0.0),
                country = "",
                population = 0,
                timezone = 0,
                sunrise = 1234,
                sunset = 1234
            )

        )

        val newDomain = CityWeatherMapper.toDomain(entity)
        val gson = Gson()


        assertEquals(gson.toJson(goodDomain), gson.toJson(newDomain))
    }

    @Test
    fun checking_the_mapper_for_conversion_from_domain_WeatherForecastResponse_to_entity(){
        val goodEntity = CityWeatherEntity(
            cityId = 0,
            cityName = "Test City",
            sunrise = 1234,
            sunset = 1234,
            forecast = "123#36.6#36.6#36.6#36.6#43#80#45#45.512312#23#101.1#2021-01-01 15:00]["+
                    "123#36.6#36.6#36.6#36.6#43#80#45#45.512312#23#101.1#2021-01-01 15:00]["
        )

        val domain = WeatherForecastResponse(
            cod="200 db_data",
            message = 0,
            cnt = 40,
            list = listOf(
                Forecast(
                    dt=123,
                    main = MainForecast(
                        temp = 36.6,
                        feels_like = 36.6,
                        temp_min = 36.6,
                        temp_max = 36.6,
                        pressure = 43,
                        sea_level = null,
                        grnd_level = null,
                        humidity = 80,
                        temp_kf = 0.0
                    ),
                    emptyList(),
                    clouds = Clouds( all = 45),
                    wind = Wind(
                        speed = 45.512312,
                        deg=0,
                        gust = null
                    ),
                    visibility = 23,
                    pop = 101.1,
                    sys = Sys(""),
                    dt_txt = "2021-01-01 15:00"
                ),
                Forecast(
                    dt=123,
                    main = MainForecast(
                        temp = 36.6,
                        feels_like = 36.6,
                        temp_min = 36.6,
                        temp_max = 36.6,
                        pressure = 43,
                        sea_level = null,
                        grnd_level = null,
                        humidity = 80,
                        temp_kf = 0.0
                    ),
                    emptyList(),
                    clouds = Clouds( all = 45),
                    wind = Wind(
                        speed = 45.512312,
                        deg=0,
                        gust = null
                    ),
                    visibility = 23,
                    pop = 101.1,
                    sys = Sys(""),
                    dt_txt = "2021-01-01 15:00"
                ),

                ),
            city= City(
                id= 0,
                name = "Test City",
                coord = Coord(0.0,0.0),
                country = "",
                population = 0,
                timezone = 0,
                sunrise = 1234,
                sunset = 1234
            )

        )

        val newEntity = CityWeatherMapper.toEntity(domain)


        val gson = Gson()


        assertEquals(gson.toJson(goodEntity.forecast), gson.toJson(newEntity.forecast))
    }
}