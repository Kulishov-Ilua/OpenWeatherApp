package ru.kulishov.openweatherapp.presentation.ui.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.data.remote.model.Forecast

@Composable
fun WeatherParamsBlockUI(
    state: Int,
    onClick: (Int) -> Unit,
    weather: Forecast,
    primaryColor: Color,
    textStyle: TextStyle
) {
    println(state)
    Box(Modifier.fillMaxWidth()) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    Modifier
                        .height(30.dp)
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
                        "Темп: ${weather.main.temp}°С",
                        style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        )
                    )
                }
                Box(
                    Modifier
                        .height(30.dp)
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
                        "Ветер: ${weather.wind.speed} м/с",
                        style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        )
                    )
                }
                Box(
                    Modifier
                        .height(30.dp)
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
                        "Давлен: ${weather.main.pressure} мм/рт",
                        style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        )
                    )
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Box(
                    Modifier
                        .height(30.dp)
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
                        "Влажность: ${weather.main.humidity}%",
                        style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        )
                    )
                }
                Box(
                    Modifier
                        .height(30.dp)
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
                        "Облака: ${weather.clouds.all}",
                        style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        )
                    )
                }
                Box(
                    Modifier
                        .height(30.dp)
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
                        "Видимость: ${weather.visibility}м",
                        style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        )
                    )
                }
            }
        }
    }
}