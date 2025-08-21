package ru.kulishov.openweatherapp.presentation.viewmodel.weather

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import retrofit2.Retrofit
import ru.kulishov.openweatherapp.data.local.mapper.WeatherForecastMapper
import ru.kulishov.openweatherapp.data.remote.api.cityRequest
import ru.kulishov.openweatherapp.data.remote.model.City
import ru.kulishov.openweatherapp.data.remote.model.Clouds
import ru.kulishov.openweatherapp.data.remote.model.Coord
import ru.kulishov.openweatherapp.data.remote.model.Forecast
import ru.kulishov.openweatherapp.data.remote.model.MainForecast
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.data.remote.model.Sys
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.data.remote.model.Wind
import ru.kulishov.openweatherapp.domain.usecase.weather.GetCityWeatherByNameUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.InsertCityWeatherUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.UpdateCityWeatherUseCase
import ru.kulishov.openweatherapp.presentation.viewmodel.BaseViewModel
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Collections.emptyList


class CityWeatherViewModel(
    val getCityWeatherByNameUseCase: GetCityWeatherByNameUseCase,
    val updateCityWeatherUseCase: UpdateCityWeatherUseCase,
    val insertCityWeatherUseCase: InsertCityWeatherUseCase,
    val retrofit: Retrofit
): BaseViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _cityName = MutableStateFlow<SelectedCity>(SelectedCity(0,"",""))
    val cityName: StateFlow<SelectedCity> = _cityName.asStateFlow()

    private val _paramState = MutableStateFlow<Int>(0)
    val paramState: StateFlow<Int> = _paramState.asStateFlow()


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

    private val _weatherListWithDate = MutableStateFlow<List<Pair<Int, MutableList<Forecast>>>>(emptyList())
    val weatherListWithDate: StateFlow<List<Pair<Int, MutableList<Forecast>>>> = _weatherListWithDate.asStateFlow()

    private val _weatherListCurrentDayWithDate = MutableStateFlow<List<Forecast>>(emptyList())
    val weatherListCurrentDayWithDate: StateFlow<List<Forecast>> = _weatherListCurrentDayWithDate.asStateFlow()

    private val _selectedDay = MutableStateFlow<Int>(LocalDateTime.now().dayOfMonth)
    val selecteDay: StateFlow<Int> = _selectedDay.asStateFlow()
    private val _selectedTime = MutableStateFlow<Int>(LocalDateTime.now().hour)
    val selectedTime: StateFlow<Int> = _selectedTime.asStateFlow()


    init{
        //loadWeather()

    }
    fun loadWeather(city: SelectedCity) {
        launch {
            println("launch")
            _cityName.value=city
            _uiState.value = UiState.Loading

            try {
                val weatherFromDb = getCityWeatherByNameUseCase(city.enName).firstOrNull()


                if (weatherFromDb != null&&weatherFromDb.isNotEmpty()) {
                    println("db")
                    _weatherForecat.value = weatherFromDb.first()
                    val fForecast = findTodayCurrentHourForecast(weatherForecast.value.list)
                    if(fForecast!=null){
                        _currentForecast.value=fForecast
                        sortedForecastForDate()
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
                        city = city.enName,
                        onSuccess = { weather ->
                            apiWeather = weather
                            val forecast = WeatherForecastMapper.toForecastWithDate(weather)
                            _weatherForecat.value = forecast

                            updateDatabase(weather, weatherFromDb.isNotEmpty())
                            val fForecast = findTodayCurrentHourForecast(weatherForecast.value.list)
                            if(fForecast!=null){
                                _currentForecast.value=fForecast
                                sortedForecastForDate()
                            }else{
                                _uiState.value= UiState.Error("Not data")
                            }
                            _uiState.value = UiState.Success

                        },
                        onFailure = { e ->
                            handleApiFailure(
                                if(weatherFromDb.isEmpty()) null
                                        else
                                weatherFromDb.first(), e)
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

    fun sortedForecastForDate(){
        _weatherListWithDate.value=emptyList()
        _weatherListCurrentDayWithDate.value=emptyList()
        for(x in weatherForecast.value.list){
            val date = Instant.ofEpochMilli(x.dt*1000-10800000+60000)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()
            val dayFind = _weatherListWithDate.value.find { it->
                it.first==date.dayOfMonth
            }
            if(date.dayOfMonth==selecteDay.value){
                _weatherListCurrentDayWithDate.value+=x
            }

            if(dayFind==null){
                _weatherListWithDate.value +=Pair(date.dayOfMonth,mutableListOf(
                    x
                ))
            }else{
                dayFind.second+=x
            }
        }
    }
    private fun handleApiFailure(cachedWeather: WeatherForecastResponceWithDateTime?, error: String) {
        if (cachedWeather != null) {
            _uiState.value = UiState.EnternetError(cachedWeather.update)
        } else {
            _uiState.value = UiState.Error("Network error: $error")
        }
    }

    fun setParamState(state:Int){
        _paramState.value=state
    }

    fun setSelectedDay(day:Int){
        _selectedDay.value=day
        sortedForecastForDate()
    }
    fun setSelectedTime(hour:Int){
        _selectedTime.value=hour
    }
    fun updateCurrentForecast(forecast: Forecast){
        val forecastTime =   Instant.ofEpochMilli(forecast.dt*1000-10800000+60000)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
        _currentForecast.value=forecast
        setSelectedTime(forecastTime.hour)
    }
    sealed class UiState{
        object Loading: UiState()
        object Success: UiState()
        data class EnternetError(val timeInfo:Long):UiState()
        data class Error(val message: String) : UiState()
    }
}