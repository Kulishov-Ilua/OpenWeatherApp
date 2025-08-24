package ru.kulishov.openweatherapp.presentation.ui.components.weather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.domain.model.UiState
import ru.kulishov.openweatherapp.presentation.ui.components.app.ErrorMessageBoxUI
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.GeoWeatherViewModel

@Composable
fun GeoWeatherUi(
    viewModel: GeoWeatherViewModel
) {
    val currentForecast = viewModel.currentForecast.collectAsState()
    val curentForecastList = viewModel.weatherListCurrentDayWithDate.collectAsState()
    val forecastList = viewModel.weatherListWithDate.collectAsState()
    val selectedHour = viewModel.selectedTime.collectAsState()
    val selectedDay = viewModel.selecteDay.collectAsState()
    val paramsState = viewModel.paramState.collectAsState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val weatherResponse = viewModel.weatherForecast.collectAsState()
    when (uiState.value) {
        is UiState.locationEnabled -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ErrorMessageBoxUI(
                    stringResource(R.string.geo_error),
                )
                Box(
                    Modifier
                        .padding(bottom = 25.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        onClick = { viewModel.getForecast() },
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10),
                    ) {
                        Text(
                            stringResource(R.string.update),
                            style = TextStyle(
                                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                            )
                        )
                    }
                }
            }
        }

        is UiState.NotPermission -> {
            Column(
                modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ErrorMessageBoxUI(
                    message = stringResource(R.string.perm_geo_error),
                )
                Box(
                    Modifier
                        .padding(bottom = 15.dp)
                        .fillMaxSize(),
                    contentAlignment = Alignment.BottomCenter
                ) {
                    Button(
                        onClick = { viewModel.getForecast() },
                        modifier = Modifier
                            .padding(top = 25.dp)
                            .fillMaxWidth()
                            .height(50.dp),
                        shape = RoundedCornerShape(10),
                    ) {
                        Text(
                            stringResource(R.string.update),
                            style = TextStyle(
                                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                            )
                        )
                    }
                }
            }
        }

        is UiState.Loading -> {
            CircularProgressIndicator()
        }

        is UiState.InternetError -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                ErrorMessageBoxUI(
                    message = stringResource(R.string.there_is_no_internet_connection),
                )
            }

        }

        is UiState.Error -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ErrorMessageBoxUI(
                    message = stringResource(R.string.data_is_missing),
                )
            }
        }

        is UiState.Success -> {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(25.dp)
            ) {
                CurrentWeatherBlockUI(
                    weather = currentForecast.value,
                )
                WeatherParamsBlockUI(
                    state = paramsState.value,
                    onClick = { state ->
                        viewModel.setParamState(state)
                    },
                    weather = currentForecast.value,
                )
                HoursWeatherBlock(
                    state = paramsState.value,
                    onClick = { forecast ->
                        viewModel.updateCurrentForecast(forecast)
                    },
                    listForecast = curentForecastList.value,
                    selectedHour = selectedHour.value,
                )
                DaysWeatherBlockUI(
                    onClick = { day ->
                        viewModel.setSelectedDay(day)
                    },
                    listForecast = forecastList.value,
                    selectedDay = selectedDay.value,
                )
            }
        }
    }
}