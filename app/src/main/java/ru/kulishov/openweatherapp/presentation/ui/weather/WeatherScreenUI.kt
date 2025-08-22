package ru.kulishov.openweatherapp.presentation.ui.weather

import androidx.compose.foundation.gestures.detectHorizontalDragGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import retrofit2.Retrofit
import ru.kulishov.openweatherapp.presentation.ui.components.app.PagerIndicator
import ru.kulishov.openweatherapp.presentation.ui.components.weather.CityWeatherUI
import ru.kulishov.openweatherapp.presentation.ui.components.weather.GeoWeatherUi
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.CityWeatherViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.GeoWeatherViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.WeatherNavigationViewModel

@Composable
fun WeatherScreenUi(
    geoWeatherViewModel: GeoWeatherViewModel,
    weatherNavigationViewModel: WeatherNavigationViewModel,
    cityWeatherViewModel: CityWeatherViewModel,
    retrofit: Retrofit,
    primaryColor: Color,
    textStyle: TextStyle
) {
    val selectedCities = weatherNavigationViewModel.selectedCities.collectAsState()
    val currentPage = weatherNavigationViewModel.currentPage.collectAsState()
    val isSwipeBlocked = weatherNavigationViewModel.isSwipeBlocked.collectAsState()
    Box(
        Modifier
            .fillMaxSize()
            .pointerInput(Unit) {
                detectHorizontalDragGestures { change, dragAmount ->
                    if (isSwipeBlocked.value) return@detectHorizontalDragGestures
                    weatherNavigationViewModel.blockedSwipe()
                    change.consume()
                    if (dragAmount > 0) {
                        if (currentPage.value > 0) {
                            if (currentPage.value != 1) {
                                cityWeatherViewModel.loadWeather(selectedCities.value[currentPage.value - 2])
                            }
                            weatherNavigationViewModel.pageChanged(currentPage.value - 1)
                        }
                    } else {
                        if (currentPage.value < selectedCities.value.size) {
                            cityWeatherViewModel.loadWeather(selectedCities.value[currentPage.value])
                            weatherNavigationViewModel.pageChanged(currentPage.value + 1)
                        }
                    }
                }
            },
        contentAlignment = Alignment.TopCenter
    ) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(25.dp)
        ) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp)
            ) {
                Text(
                    if (currentPage.value == 0) "Ваше местоположение"
                    else selectedCities.value[currentPage.value - 1].localName,
                    style = TextStyle(
                        fontFamily = textStyle.fontFamily,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontStyle = textStyle.fontStyle
                    )
                )
                PagerIndicator(
                    pageCount = selectedCities.value.size + 1,
                    currentPageIndex = currentPage.value,
                    primaryColor = primaryColor
                )
                if (currentPage.value == 0) {
                    GeoWeatherUi(
                        viewModel = geoWeatherViewModel,
                        primaryColor = primaryColor,
                        textStyle = textStyle
                    )
                } else {
                    CityWeatherUI(
                        viewModel = cityWeatherViewModel,
                        primaryColor = primaryColor,
                        textStyle = textStyle
                    )
                }
            }
        }
    }

}