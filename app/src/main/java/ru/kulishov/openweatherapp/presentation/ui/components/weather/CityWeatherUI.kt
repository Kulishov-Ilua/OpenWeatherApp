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
                ErrorMessageBoxUI("Отсутствует подключение к интернету!\nДанные актуальны на:${currentForecast.value.dt_txt}",textStyle)
                CurrentWeatherBlockUI(currentForecast.value,primaryColor,textStyle)
            }

        }
        is CityWeatherViewModel.UiState.Error -> {
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally) {
                ErrorMessageBoxUI("Данные отсутствуют!",textStyle)
            }
        }
        is CityWeatherViewModel.UiState.Success ->{
            Column(modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)) {
                CurrentWeatherBlockUI(currentForecast.value,primaryColor,textStyle)
                WeatherParamsBlockUI(paramsState.value,
                    {state->
                        viewModel.setParamState(state)
                    }, currentForecast.value,primaryColor,textStyle)
                HoursWeatherBlock(
                    paramsState.value,
                    {
                        forecast->
                        viewModel.updateCurrentForecast(forecast)
                    },
                    curentForecastList.value,
                    selectedHour.value,
                    primaryColor,
                    textStyle
                )
                DaysWeatherBlockUI(
                    { day->
                        viewModel.setSelectedDay(day)
                    },
                    forecastList.value,
                    selectedDay.value,
                    primaryColor,
                    textStyle
                )
            }
        }
    }



}