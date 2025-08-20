package ru.kulishov.openweatherapp.presentation.ui.components.weather

import android.media.Image
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.data.remote.model.Forecast
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun HoursWeatherBlock(
    state:Int,
    onClick:(forecast: Forecast)->Unit,
    listForecast:List<Forecast>,
    selectedHour:Int,
    primaryColor: Color,
    textStyle: TextStyle
){
    LazyRow(verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        modifier = Modifier.fillMaxWidth()) {
        items(listForecast){
            forecast->
            val forecastTime =   Instant.ofEpochMilli(forecast.dt*1000-10800000+60000)
            .atZone(ZoneId.systemDefault())
            .toLocalDateTime()
            Box(Modifier
                .clickable {
                    onClick(forecast)
                }
                .width(70.dp).height(80.dp).clip(RoundedCornerShape(10)), contentAlignment = Alignment.Center){
                println(selectedHour)
                println(forecastTime.hour)
                if((selectedHour-forecastTime.hour)<3&&(selectedHour-forecastTime.hour)>=0){
                    Image(painter = painterResource(R.drawable.weather_params_background), contentDescription = "background",
                        modifier = Modifier.fillMaxHeight(),
                        contentScale = ContentScale.FillHeight)
                }
                Column(
                   modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Text(forecastTime.hour.toString(), style = TextStyle(
                        fontFamily = textStyle.fontFamily,
                        color = primaryColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontStyle = textStyle.fontStyle
                    ))
                    when(state){
                        1->{
                            Icon(painter = painterResource(R.drawable.outline_air_24),
                                contentDescription = "wind",
                                tint = primaryColor,
                                modifier = Modifier.size(20.dp))
                        }
                        2->{
                            Text(forecast.main.pressure.toString(), style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                        3->{
                            Icon(painter = painterResource(
                                if(forecast.main.humidity<50){
                                    R.drawable.outline_humidity_low_24
                                }else if(forecast.main.humidity<80){
                                    R.drawable.outline_humidity_mid_24
                                }else{
                                    R.drawable.outline_humidity_high_24
                                }
                                ),
                                contentDescription = "humidity",
                                tint = primaryColor,
                                modifier = Modifier.size(20.dp))
                        }
                        4->{
                            Image(painter = painterResource(
                                if(forecast.clouds.all<20){
                                    R.drawable.sun
                                }else{
                                    R.drawable.cloud
                                }
                            ),
                                contentDescription = "cloud",
                                modifier = Modifier.size(20.dp))
                        }
                        5->{
                            Text(forecast.visibility.toString(), style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                        else->{
                            Image(painter = painterResource(
                                if(forecast.main.humidity>80) R.drawable.rain
                                else if (forecast.clouds.all>10) R.drawable.cloud
                                else R.drawable.sunpng
                            ),
                                contentDescription = "cloud",
                                modifier = Modifier.size(20.dp))
                        }
                    }

                    when(state){
                        1->{
                            Text("${forecast.wind.speed} м/с", style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                        2->{
                            Text("мм/рт", style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                        3->{
                            Text("${forecast.main.humidity}%", style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                        4->{
                            Text("${forecast.clouds.all}", style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                        5->{
                            Text("м", style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                        else->{
                            Text("${forecast.main.temp}°С", style = TextStyle(
                                fontFamily = textStyle.fontFamily,
                                color = primaryColor,
                                fontWeight = FontWeight.Medium,
                                fontSize = 14.sp,
                                fontStyle = textStyle.fontStyle
                            ))
                        }
                    }
                }
            }

        }

    }

}