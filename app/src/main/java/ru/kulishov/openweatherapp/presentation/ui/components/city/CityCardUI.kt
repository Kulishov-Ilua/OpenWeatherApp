package ru.kulishov.openweatherapp.presentation.ui.components.city

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.domain.model.UiState
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.CityWeatherViewModel

@Composable
fun CityCardUI(
    viewModel: CityWeatherViewModel,
    onTap: () -> Unit
) {
    val curentForecastList = viewModel.weatherListCurrentDayWithDate.collectAsState()
    val uiState = viewModel.uiState.collectAsStateWithLifecycle()
    val cityName = viewModel.cityName.collectAsState()

    Box(
        Modifier
            .fillMaxWidth()
            .height(60.dp),
        contentAlignment = Alignment.Center
    ) {
        Image(
            painter = painterResource(R.drawable.city_card_bacground),
            contentDescription = "backgroung",
            Modifier.fillMaxSize()
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(start = 25.dp, end = 25.dp)
                .fillMaxSize(),
            horizontalArrangement = Arrangement.Start
        ) {
            Icon(
                painter = painterResource(R.drawable.exit),
                contentDescription = "delete",
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.clickable {
                    onTap()
                })
            Text(
                cityName.value.localName,
                style = TextStyle(
                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                    color = MaterialTheme.colorScheme.onSurface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp,
                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                ),
                modifier = Modifier.padding(start = 15.dp)
            )
            Box(
                Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)
                ) {
                    when (uiState.value) {
                        is UiState.Loading -> {
                            CircularProgressIndicator()
                        }

                        is UiState.InternetError -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    "${curentForecastList.value[curentForecastList.value.size / 2].main.temp_min}°С",
                                    style = TextStyle(
                                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                        color = Color(111, 121, 118),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                    )
                                )
                                Text(
                                    "${curentForecastList.value[curentForecastList.value.size / 2].main.temp_max}°С",
                                    style = TextStyle(
                                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                    )
                                )
                            }
                        }

                        is UiState.Success -> {
                            Row(
                                verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)
                            ) {
                                Text(
                                    "${curentForecastList.value[curentForecastList.value.size / 2].main.temp_min}°С",
                                    style = TextStyle(
                                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                        color = Color(111, 121, 118),
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                    )
                                )
                                Text(
                                    "${curentForecastList.value[curentForecastList.value.size / 2].main.temp_max}°С",
                                    style = TextStyle(
                                        fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                        color = MaterialTheme.colorScheme.onSurface,
                                        fontWeight = FontWeight.Bold,
                                        fontSize = 16.sp,
                                        fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                    )
                                )
                            }
                        }

                        is UiState.Error -> {
                            Text(
                                stringResource(R.string.not_data),
                                style = TextStyle(
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                )
                            )
                        }

                        is UiState.locationEnabled -> {}
                        is UiState.NotPermission -> {}
                    }
                }
            }
        }

    }
}