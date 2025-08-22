package ru.kulishov.openweatherapp

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import retrofit2.Retrofit
import ru.kulishov.openweatherapp.domain.usecase.weather.GetCityWeatherByNameUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.InsertCityWeatherUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.UpdateCityWeatherUseCase
import ru.kulishov.openweatherapp.presentation.ui.cities.SelectedCityScreen
import ru.kulishov.openweatherapp.presentation.ui.weather.WeatherScreenUi
import ru.kulishov.openweatherapp.presentation.viewmodel.cities.CitiesScreenViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.cities.CitySearchViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.CityWeatherViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.GeoWeatherViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.WeatherNavigationViewModel
import ru.kulishov.openweatherapp.ui.theme.OpenWeatherAppTheme
import javax.inject.Inject

@AndroidEntryPoint

class MainActivity : ComponentActivity() {
    @Inject
    lateinit var retrofit: Retrofit

    @Inject
    lateinit var getCityWeatherByNameUseCase: GetCityWeatherByNameUseCase

    @Inject
    lateinit var updateCityWeatherUseCase: UpdateCityWeatherUseCase

    @Inject
    lateinit var insertWeatherByNameUseCase: InsertCityWeatherUseCase

    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {

            val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
            var navState by remember { mutableStateOf(true) }


            val weatherScreenWeatherViewModel: CityWeatherViewModel = hiltViewModel()
            val searchViewModel: CitySearchViewModel = hiltViewModel()
            val selectedCityScreenViewModel: CitiesScreenViewModel = hiltViewModel()
            val weatherNavigationViewModel: WeatherNavigationViewModel = hiltViewModel()


            val geoWeatherViewModel = GeoWeatherViewModel(
                retrofit = retrofit,
                context = this,
                locationManager = locationManager
            )

            OpenWeatherAppTheme {
                Box(
                    Modifier
                        .systemBarsPadding()
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier.padding(top = 50.dp, start = 25.dp, end = 25.dp),
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (navState) {
                            WeatherScreenUi(
                                geoWeatherViewModel = geoWeatherViewModel,
                                weatherNavigationViewModel = weatherNavigationViewModel,
                                cityWeatherViewModel = weatherScreenWeatherViewModel,
                                retrofit = retrofit,
                                primaryColor = MaterialTheme.colorScheme.onSurface,
                                textStyle = MaterialTheme.typography.bodyMedium
                            )
                        } else {
                            SelectedCityScreen(
                                selectedCityViewModel = selectedCityScreenViewModel,
                                searchViewModel = searchViewModel, getCityWeatherByNameUseCase,
                                updateCityWeatherUseCase = updateCityWeatherUseCase,
                                insertCityWeatherUseCase = insertWeatherByNameUseCase,
                                primaryColor = MaterialTheme.colorScheme.onSurface,
                                textStyle = MaterialTheme.typography.bodyMedium,
                                retrofit = retrofit,
                                onExit = { navState = true })
                        }

                    }

                }
                if (navState) {
                    Box(
                        Modifier
                            .padding(top = 50.dp, start = 25.dp)
                            .clickable {
                                navState = false
                            }) {
                        Icon(
                            painter = painterResource(R.drawable.menu),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }


        }
    }
}
