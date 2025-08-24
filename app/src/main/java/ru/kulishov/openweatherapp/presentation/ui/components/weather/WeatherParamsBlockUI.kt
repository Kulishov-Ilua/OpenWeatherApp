package ru.kulishov.openweatherapp.presentation.ui.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.data.remote.model.Forecast

@Composable
fun WeatherParamsBlockUI(
    state: Int,
    onClick: (Int) -> Unit,
    weather: Forecast
) {
    println(state)
    Box(Modifier.fillMaxWidth()) {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    Modifier
                        .width(167.dp)
                        .defaultMinSize(minHeight = 30.dp)
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onClick(0)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (state == 0) {
                        Image(
                            painter = painterResource(R.drawable.weather_params_background),
                            contentDescription = "background",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Text(
                        stringResource(R.string.temp) + ": ${weather.main.temp}°С",
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Box(
                    Modifier
                        .width(167.dp)
                        .defaultMinSize(minHeight = 30.dp)
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onClick(3)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (state == 3) {
                        Image(
                            painter = painterResource(R.drawable.weather_params_background),
                            contentDescription = "background",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Text(
                        stringResource(R.string.hum) + ": ${weather.main.humidity}%",
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    Modifier
                        .width(167.dp)
                        .defaultMinSize(minHeight = 30.dp)
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onClick(1)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (state == 1) {
                        Image(
                            painter = painterResource(R.drawable.weather_params_background),
                            contentDescription = "background",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Text(
                        stringResource(R.string.wind) + ": ${weather.wind.speed} " + stringResource(
                            R.string.ms
                        ),
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Box(
                    Modifier
                        .width(167.dp)
                        .defaultMinSize(minHeight = 30.dp)
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onClick(4)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (state == 4) {
                        Image(
                            painter = painterResource(R.drawable.weather_params_background),
                            contentDescription = "background",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Text(
                        stringResource(R.string.cloud) + ":   ${weather.clouds.all}",
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Box(
                    Modifier
                        .width(167.dp)
                        .defaultMinSize(minHeight = 30.dp)
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onClick(2)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (state == 2) {
                        Image(
                            painter = painterResource(R.drawable.weather_params_background),
                            contentDescription = "background",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Text(
                        stringResource(R.string.press) + ": ${weather.main.pressure} " + stringResource(
                            R.string.mmHg
                        ),
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                Box(
                    Modifier
                        .width(167.dp)
                        .defaultMinSize(minHeight = 30.dp)
                        .height(IntrinsicSize.Min)
                        .clip(RoundedCornerShape(5.dp))
                        .clickable {
                            onClick(5)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    if (state == 5) {
                        Image(
                            painter = painterResource(R.drawable.weather_params_background),
                            contentDescription = "background",
                            modifier = Modifier.fillMaxHeight(),
                            contentScale = ContentScale.FillHeight
                        )
                    }
                    Text(
                        stringResource(R.string.visibl) + ": ${weather.visibility}" + stringResource(
                            R.string.m
                        ),
                        style = TextStyle(
                            fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = MaterialTheme.typography.bodyMedium.fontStyle,
                            textAlign = TextAlign.Center
                        )
                    )
                }
            }

        }
    }
}