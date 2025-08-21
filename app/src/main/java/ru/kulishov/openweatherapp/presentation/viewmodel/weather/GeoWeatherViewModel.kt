package ru.kulishov.openweatherapp.presentation.viewmodel.weather

import android.Manifest
import android.app.Activity
import android.appwidget.AppWidgetManager
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.content.edit
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.tasks.CancellationTokenSource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import retrofit2.Retrofit
import ru.kulishov.openweatherapp.MainActivity
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.data.local.mapper.WeatherForecastMapper
import ru.kulishov.openweatherapp.data.remote.api.cityRequest
import ru.kulishov.openweatherapp.data.remote.api.geoRequest
import ru.kulishov.openweatherapp.data.remote.model.City
import ru.kulishov.openweatherapp.data.remote.model.Clouds
import ru.kulishov.openweatherapp.data.remote.model.Coord
import ru.kulishov.openweatherapp.data.remote.model.Forecast
import ru.kulishov.openweatherapp.data.remote.model.MainForecast
import ru.kulishov.openweatherapp.data.remote.model.Sys
import ru.kulishov.openweatherapp.data.remote.model.WeatherForecastResponse
import ru.kulishov.openweatherapp.data.remote.model.Wind
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.model.WeatherForecastResponceWithDateTime
import ru.kulishov.openweatherapp.presentation.viewmodel.BaseViewModel
import ru.kulishov.openweatherapp.widget_feature.WeatherWidget
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Collections
import javax.inject.Inject
import kotlin.collections.first
import kotlin.collections.isNotEmpty

@HiltViewModel
class GeoWeatherViewModel @Inject constructor(
    private  val retrofit: Retrofit,
    private val context: Context,
    private val locationManager: LocationManager
): BaseViewModel() {

    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _cityName = MutableStateFlow<SelectedCity>(SelectedCity(0,"",""))
    val cityName: StateFlow<SelectedCity> = _cityName.asStateFlow()

    private val _paramState = MutableStateFlow<Int>(0)
    val paramState: StateFlow<Int> = _paramState.asStateFlow()

    private val MY_PERMISSIONS_REQUEST_LOCATION = 98
    private val _currentForecast = MutableStateFlow<Forecast>(Forecast(0,
        MainForecast(
            temp = 0.0,
            feels_like = 0.0,
            temp_min = 0.0,
            temp_max = 0.0,
            pressure = 0,
            sea_level = null,
            grnd_level = null,
            humidity = 0,
            temp_kf = 0.0),
        weather = Collections.emptyList(),
        clouds = Clouds(0),
        wind = Wind(
            speed = 0.0,
            deg = 0,
            gust = null),
        visibility = 0,
        pop = 0.0,
        sys = Sys(""),
        dt_txt = "")
    )
    val currentForecast:StateFlow<Forecast> = _currentForecast.asStateFlow()

    private val _weatherForecat = MutableStateFlow<WeatherForecastResponceWithDateTime>(
        WeatherForecastResponceWithDateTime(
            cod = "",
            message = 0,
            cnt = 0,
            list = Collections.emptyList(),
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

    private val _weatherListWithDate = MutableStateFlow<List<Pair<Int, MutableList<Forecast>>>>(
        Collections.emptyList()
    )
    val weatherListWithDate: StateFlow<List<Pair<Int, MutableList<Forecast>>>> = _weatherListWithDate.asStateFlow()

    private val _weatherListCurrentDayWithDate = MutableStateFlow<List<Forecast>>(Collections.emptyList())
    val weatherListCurrentDayWithDate: StateFlow<List<Forecast>> = _weatherListCurrentDayWithDate.asStateFlow()

    private val _selectedDay = MutableStateFlow<Int>(LocalDateTime.now().dayOfMonth)
    val selecteDay: StateFlow<Int> = _selectedDay.asStateFlow()
    private val _selectedTime = MutableStateFlow<Int>(LocalDateTime.now().hour)
    val selectedTime: StateFlow<Int> = _selectedTime.asStateFlow()

    private val _isApiBlocked = MutableStateFlow<Boolean>(false)
    val isApiBlocked: StateFlow<Boolean> = _isApiBlocked.asStateFlow()

    private val _location = MutableStateFlow<Location>(Location(""))
    val location: StateFlow<Location> = _location.asStateFlow()


    private var fusedLocationClient: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)

    private var cancellationTokenSource: CancellationTokenSource? = null


    init {
        getForecast()
    }
     fun getForecast(){
        if(!isLocationEnabled()){
            _uiState.value= UiState.locationEnabled
            return
        }
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(context as Activity, Manifest.permission.ACCESS_FINE_LOCATION)) {

            } else {
                ActivityCompat.requestPermissions(context as Activity,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION,
                        Manifest.permission.ACCESS_COARSE_LOCATION),
                    MY_PERMISSIONS_REQUEST_LOCATION)
            }
            _uiState.value= UiState.NotPermission
            return
        }
        fusedLocationClient.getCurrentLocation(Priority.PRIORITY_LOW_POWER,
            cancellationTokenSource?.token
        )
            .addOnCompleteListener { data->
                println("${data.result.latitude} ${data.result.longitude}")
                loadWeather(data.result.latitude,data.result.latitude)
            }
    }

    private fun blockedApi(){
        launch {
            _isApiBlocked.value=true
            delay(1000)
            _isApiBlocked.value=false
        }
    }


    fun isLocationEnabled(): Boolean = locationManager.isLocationEnabled


    private fun loadWeather(lat: Double,lon: Double) {
        launch {
            _uiState.value = UiState.Loading
                if(!isApiBlocked.value){
                    try {
                        var apiWeather: WeatherForecastResponse? = null
                        blockedApi()
                        geoRequest(
                            retrofit = retrofit,
                            lat = lat,
                            lon=lon,
                            onSuccess = { weather ->
                                val forecast = WeatherForecastMapper.toForecastWithDate(weather)
                                _weatherForecat.value = forecast

                                val fForecast = findTodayCurrentHourForecast(weatherForecast.value.list)
                                if(fForecast!=null){
                                    _currentForecast.value=fForecast
                                    onWeatherUpdated(currentForecast.value)
                                    sortedForecastForDate()
                                }else{
                                    _uiState.value= UiState.Error("Not data")
                                }
                                _uiState.value =UiState.Success

                            },
                            onFailure = { e ->
                                _uiState.value= UiState.InternetError
                            }
                        )

                    } catch (e: Exception) {
                        _uiState.value= UiState.Error(e.message!!)
                    }
                }
            }

    }




    fun findTodayCurrentHourForecast(forecasts: List<Forecast>): Forecast? {
        val currentTime = LocalDateTime.now()
        return forecasts.find { forecast ->
            val forecastDateTime = Instant.ofEpochMilli(forecast.dt*1000-10800000+60000)
                .atZone(ZoneId.systemDefault())
                .toLocalDateTime()

            forecastDateTime.year == currentTime.year &&
                    forecastDateTime.month == currentTime.month &&
                    forecastDateTime.dayOfMonth == currentTime.dayOfMonth &&
                    (currentTime.hour - forecastDateTime.hour<3)
        }
    }

    fun sortedForecastForDate(){
        _weatherListWithDate.value= Collections.emptyList()
        _weatherListCurrentDayWithDate.value= Collections.emptyList()
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

    fun onWeatherUpdated(weather: Forecast) {
        launch {
            val prefs = context.getSharedPreferences("weather_widget", Context.MODE_PRIVATE)
            prefs.edit {
                putFloat("temp", weather.main.temp.toFloat())
                putInt("state",    if (weather.main.humidity > 80) 2
                else if (weather.clouds.all > 10) 1
                else 0)

            }
            updateWidgetBroadcast()
        }
    }
    private fun updateWidgetBroadcast() {
        val intent = Intent(context, WeatherWidget::class.java).apply {
            action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        }
        val ids = AppWidgetManager.getInstance(context)
            .getAppWidgetIds(ComponentName(context, WeatherWidget::class.java))
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(intent)
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
        object NotPermission:UiState()
        object InternetError:UiState()

        object locationEnabled:UiState()
        data class Error(val message: String) : UiState()
    }
}
