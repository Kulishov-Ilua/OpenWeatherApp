package ru.kulishov.openweatherapp.data.local.data.mapper

import ru.kulishov.openweatherapp.data.local.data.entity.CityWeatherEntity
import ru.kulishov.openweatherapp.data.remote.model.City
import ru.kulishov.openweatherapp.data.remote.model.Clouds
import ru.kulishov.openweatherapp.data.remote.model.Coord
import ru.kulishov.openweatherapp.data.remote.model.Forecast
import ru.kulishov.openweatherapp.data.remote.model.MainForecast
import ru.kulishov.openweatherapp.data.remote.model.Sys
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.data.remote.model.Wind

object CityWeatherMapper {
    fun toDomain(entity: CityWeatherEntity): WeatherForecastResponse {
        val forecastList = mutableListOf<Forecast>()
        val toForecast = entity.forecast.split("][").map { forecast ->
            if (forecast.length > 5) {
                val params = forecast.split('#')
                forecastList += Forecast(
                    dt = params[0].toLong(),
                    main = MainForecast(
                        temp = params[1].toDouble(),
                        feels_like = params[2].toDouble(),
                        temp_min = params[3].toDouble(),
                        temp_max = params[4].toDouble(),
                        pressure = params[5].toInt(),
                        sea_level = null,
                        grnd_level = null,
                        humidity = params[6].toInt(),
                        temp_kf = 0.0
                    ),
                    emptyList(),
                    clouds = Clouds(
                        params[7].toInt()
                    ),
                    wind = Wind(
                        params[8].toDouble(),
                        deg = 0,
                        gust = null
                    ),
                    visibility = params[9].toInt(),
                    pop = params[10].toDouble(),
                    sys = Sys(""),
                    dt_txt = params[11]
                )
            }
        }

        return WeatherForecastResponse(
            cod = "200 db_data",
            message = 0,
            cnt = 40,
            list = forecastList,
            city = City(
                id = entity.cityId,
                name = entity.cityName,
                coord = Coord(0.0, 0.0),
                country = "",
                population = 0,
                timezone = 0,
                sunrise = entity.sunrise,
                sunset = entity.sunset
            )
        )
    }

    fun toDomainWithTime(entity: CityWeatherEntity): WeatherForecastResponceWithDateTime {
        val forecastList = mutableListOf<Forecast>()
        val toForecast = entity.forecast.split("][").map { forecast ->
            if (forecast.length > 5) {
                val params = forecast.split('#')
                forecastList += Forecast(
                    dt = params[0].toLong(),
                    main = MainForecast(
                        temp = params[1].toDouble(),
                        feels_like = params[2].toDouble(),
                        temp_min = params[3].toDouble(),
                        temp_max = params[4].toDouble(),
                        pressure = params[5].toInt(),
                        sea_level = null,
                        grnd_level = null,
                        humidity = params[6].toInt(),
                        temp_kf = 0.0
                    ),
                    emptyList(),
                    clouds = Clouds(
                        params[7].toInt()
                    ),
                    wind = Wind(
                        params[8].toDouble(),
                        deg = 0,
                        gust = null
                    ),
                    visibility = params[9].toInt(),
                    pop = params[10].toDouble(),
                    sys = Sys(""),
                    dt_txt = params[11]
                )
            }
        }

        return WeatherForecastResponceWithDateTime(
            cod = "200 db_data",
            message = 0,
            cnt = 40,
            list = forecastList,
            city = City(
                id = entity.cityId,
                name = entity.cityName,
                coord = Coord(0.0, 0.0),
                country = "",
                population = 0,
                timezone = 0,
                sunrise = entity.sunrise,
                sunset = entity.sunset
            ),
            update = entity.forecastTime
        )
    }

    fun toEntity(domain: WeatherForecastResponse): CityWeatherEntity {
        var forecastList = ""
        for (forecast in domain.list) {
            forecastList += "${forecast.dt}#${forecast.main.temp}#${forecast.main.feels_like}#${forecast.main.temp_min}#" +
                    "${forecast.main.temp_max}#${forecast.main.pressure}#${forecast.main.humidity}#${forecast.clouds.all}#" +
                    "${forecast.wind.speed}#${forecast.visibility}#${forecast.pop}#${forecast.dt_txt}]["
        }
        return CityWeatherEntity(
            cityId = domain.city.id,
            cityName = domain.city.name,
            sunrise = domain.city.sunrise,
            sunset = domain.city.sunset,
            forecast = forecastList
        )
    }
}