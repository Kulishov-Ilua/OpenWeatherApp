package ru.kulishov.openweatherapp.presentation.viewmodel.weather

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Retrofit
import ru.kulishov.openweatherapp.data.local.mapper.WeatherForecastMapper
import ru.kulishov.openweatherapp.data.remote.cityRequest
import ru.kulishov.openweatherapp.domain.model.City
import ru.kulishov.openweatherapp.domain.model.Clouds
import ru.kulishov.openweatherapp.domain.model.Coord
import ru.kulishov.openweatherapp.domain.model.Forecast
import ru.kulishov.openweatherapp.domain.model.MainForecast
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.model.Sys
import ru.kulishov.openweatherapp.domain.model.Weather
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.domain.model.Wind
import ru.kulishov.openweatherapp.domain.usecase.weather.GetCityWeatherByNameUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.GetCityWeatherUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.InsertCityWeatherUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.UpdateCityWeatherUseCase
import ru.kulishov.openweatherapp.presentation.viewmodel.BaseViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar


class CityWeatherViewModel(
    val getCityWeatherByNameUseCase: GetCityWeatherByNameUseCase,
    val updateCityWeatherUseCase: UpdateCityWeatherUseCase,
    val insertCityWeatherUseCase: InsertCityWeatherUseCase,
    val cityName: SelectedCity,
    val retrofit: Retrofit
): BaseViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()


    private val _currentForecast = MutableStateFlow<Forecast>(Forecast(0,
        MainForecast(0.0,
            0.0,
            0.0,
            0.0,
            0,
            null,
            null,
            0,
            0.0),
        emptyList(),
        Clouds(0),
        Wind(0.0,0,null),
        0,
        0.0,
        Sys(""),
        ""))
    val currentForecast:StateFlow<Forecast> = _currentForecast.asStateFlow()

    private val _weatherForecat = MutableStateFlow<WeatherForecastResponceWithDateTime>(
        WeatherForecastResponceWithDateTime(
        cod = "",
        message = 0,
        cnt = 0,
        list = emptyList(),
        city = City(
            id=0,
            name="",
            coord = Coord(0.0,0.0),
            country = "",
            population = 0,
            timezone = 0,
            sunrise = 0,
            sunset = 0
        ),
            update = 0
    )
    )
    val weatherForecast: StateFlow<WeatherForecastResponceWithDateTime> = _weatherForecat.asStateFlow()

    private val _weatherCitiesDb = MutableStateFlow<List<WeatherForecastResponceWithDateTime>>(emptyList())
    val weatherCitiesDb: StateFlow<List<WeatherForecastResponceWithDateTime>> =_weatherCitiesDb.asStateFlow()
    init{
        loadWeather()

    }
    fun loadWeather() {
        launch {
            println("launch")
            _uiState.value = UiState.Loading

            try {
                val weatherFromDb = getCityWeatherByNameUseCase(cityName.enName).firstOrNull()


                if (weatherFromDb != null&&weatherFromDb.isNotEmpty()) {
                    println("db")
                    _weatherForecat.value = weatherFromDb.first()
                    val fForecast = findTodayCurrentHourForecast(weatherForecast.value.list)
                    if(fForecast!=null){
                        _currentForecast.value=fForecast
                    }else{
                        _uiState.value= UiState.Error("Not data")
                    }
                }

                val shouldUpdateFromApi = weatherFromDb!!.isEmpty()||shouldUpdateFromApi(weatherFromDb?.first()!!.update)

                if (!shouldUpdateFromApi) {
                    _uiState.value = UiState.Success
                    return@launch
                }

                try {
                    var apiWeather: WeatherForecastResponse? = null

                    cityRequest(
                        retrofit = retrofit,
                        city = cityName.enName,
                        onSuccess = { weather ->
                            apiWeather = weather
                            val forecast = WeatherForecastMapper.toForecastWithDate(weather)
                            _weatherForecat.value = forecast

                            updateDatabase(weather, weatherFromDb.isNotEmpty())
                            val fForecast = findTodayCurrentHourForecast(weatherForecast.value.list)
                            if(fForecast!=null){
                                _currentForecast.value=fForecast
                            }else{
                                _uiState.value= UiState.Error("Not data")
                            }
                            _uiState.value = UiState.Success

                        },
                        onFailure = { e ->
                            handleApiFailure(weatherFromDb.first(), e)
                        }
                    )

                } catch (e: Exception) {
                    handleApiFailure(weatherFromDb.first(), e.message!!)
                }

            } catch (e: Exception) {
                _uiState.value = UiState.Error("Database error: ${e.message}")
            }
        }
    }

    private fun shouldUpdateFromApi(lastUpdate: Long?): Boolean {
        if (lastUpdate == null) return true

        val currentTime = System.currentTimeMillis()
        val threeHoursInMillis = 3 * 60 * 60 * 1000
        return (currentTime - lastUpdate) > threeHoursInMillis
    }

    private  fun updateDatabase(weather: WeatherForecastResponse, hasExistingData: Boolean) {
        launch {
            try {
                if (hasExistingData) {
                    updateCityWeatherUseCase(weather)
                } else {
                    insertCityWeatherUseCase(weather)
                }
            }catch (e: Exception){
                println("Database update failed: ${e.message}")
            }
        }

    }

    fun findTodayCurrentHourForecast(forecasts: List<Forecast>): Forecast? {
       val currentTime = LocalDateTime.now()

        return forecasts.find { forecast ->
            val forecastDateTime = Instant.ofEpochMilli(forecast.dt*1000-10800000+60000)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            println(forecastDateTime)

            forecastDateTime.year == currentTime.year &&
                    forecastDateTime.month == currentTime.month &&
                    forecastDateTime.dayOfMonth == currentTime.dayOfMonth &&
                    (currentTime.hour - forecastDateTime.hour<3)
        }
    }
    private fun handleApiFailure(cachedWeather: WeatherForecastResponceWithDateTime?, error: String) {
        if (cachedWeather != null) {
            _uiState.value = UiState.EnternetError(cachedWeather.update)
        } else {
            _uiState.value = UiState.Error("Network error: $error")
        }
    }
    sealed class UiState{
        object Loading: UiState()
        object Success: UiState()
        data class EnternetError(val timeInfo:Long):UiState()
        data class Error(val message: String) : UiState()
    }
}