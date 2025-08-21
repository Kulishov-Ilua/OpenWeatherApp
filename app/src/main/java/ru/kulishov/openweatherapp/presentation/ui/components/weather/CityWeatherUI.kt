package ru.kulishov.openweatherapp.presentation.ui.components.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.kulishov.openweatherapp.presentation.ui.components.app.ErrorMessageBoxUI
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.CityWeatherViewModel

@Composable
fun CityWeatherUI(
    viewModel: CityWeatherViewModel,
    primaryColor: Color,
    textStyle: TextStyle
){
    val currentForecast = viewModel.currentForecast.collectAsState()
    val curentForecastList = viewModel.weatherListCurrentDayWithDate.collectAsState()
    val forecastList = viewModel.weatherListWithDate.collectAsState()
    val selectedHour = viewModel.selectedTime.collectAsState()
    val selectedDay = viewModel.selecteDay.collectAsState()
    val paramsState = viewModel.paramState.collectAsState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    when (uiState.value) {
        is CityWeatherViewModel.UiState.Loading ->{
            CircularProgressIndicator()
        }
        is CityWeatherViewModel.UiState.EnternetError -> {
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)) {
                ErrorMessageBoxUI(
                    message = "Отсутствует подключение к интернету!\nДанные актуальны на:${currentForecast.value.dt_txt}",
                    textStyle = textStyle)
                CurrentWeatherBlockUI(
                    weather = currentForecast.value,
                    primaryColor = primaryColor,
                    textStyle = textStyle)
            }

        }
        is CityWeatherViewModel.UiState.Error -> {
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ErrorMessageBoxUI(
                    message = "Данные отсутствуют!",
                    textStyle = textStyle)
            }
        }
        is CityWeatherViewModel.UiState.Success ->{
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)) {
                CurrentWeatherBlockUI(
                    weather = currentForecast.value,
                    primaryColor = primaryColor,
                    textStyle = textStyle)
                WeatherParamsBlockUI(
                    state = paramsState.value,
                    onClick = {state->
                        viewModel.setParamState(state)
                    },
                    weather = currentForecast.value,
                    primaryColor = primaryColor,
                    textStyle = textStyle)
                HoursWeatherBlock(
                    state = paramsState.value,
                    onClick = {
                        forecast->
                        viewModel.updateCurrentForecast(forecast)
                    },
                    listForecast = curentForecastList.value,
                    selectedHour = selectedHour.value,
                    primaryColor = primaryColor,
                    textStyle = textStyle
                )
                DaysWeatherBlockUI(
                    onClick = { day->
                        viewModel.setSelectedDay(day)
                    },
                    listForecast = forecastList.value,
                    selectedDay = selectedDay.value,
                    primaryColor = primaryColor,
                    textStyle = textStyle
                )
            }
        }
    }



}