package ru.kulishov.openweatherapp.presentation.ui.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun DaysWeatherBlockUI(
    onClick:(day: Int)->Unit,
    listForecast:List<Pair<Int, List<Forecast>>>,
    selectedDay:Int,
    primaryColor: Color,
    textStyle: TextStyle
){
    val currentDate = LocalDateTime.now().dayOfMonth
    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ){
        items(listForecast){
            day->
            var dayInWeek = ""
            if(day.second.isNotEmpty()){
                val forecastDateTime = Instant.ofEpochMilli(day.second.first().dt*1000-10800000+60000)
                    .atZone(ZoneId.systemDefault())
                    .toLocalDateTime()
                dayInWeek=when(forecastDateTime.dayOfWeek.value){
                    1->"Пн"
                    2-> "Вт"
                    3-> "Ср"
                    4-> "Чт"
                    5->"Пт"
                    6->"Сб"
                    else -> "Вс"
                }
            }
            Box(modifier = Modifier
                .clickable{
                    onClick(day.first)
                }
                .height(60.dp).fillMaxWidth(),
                contentAlignment = Alignment.Center){
                if(selectedDay==day.first){
                    Image(painter = painterResource(R.drawable.city_card_bacground), contentDescription = "background",
                        modifier = Modifier.fillMaxHeight(),
                        contentScale = ContentScale.FillHeight)
                }
                Row(modifier = Modifier.padding(15.dp).fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                    Text(text = if(currentDate==day.first)"Сегодня"
                    else dayInWeek, style = TextStyle(
                        fontFamily = textStyle.fontFamily,
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        fontStyle = textStyle.fontStyle
                    )
                        )
                    Text(text = if(day.second.isNotEmpty()){
                        "(${Instant.ofEpochMilli( day.second.first().dt*1000-10800000+60000)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime().dayOfMonth}.${Instant.ofEpochMilli( day.second.first().dt*1000-10800000+60000)
                            .atZone(ZoneId.systemDefault())
                            .toLocalDateTime().month.ordinal})"

                    } else "", style = TextStyle(
                        fontFamily = textStyle.fontFamily,
                        color = primaryColor,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp,
                        fontStyle = textStyle.fontStyle
                    )
                    )
                    Box(Modifier.fillMaxWidth(),
                        contentAlignment = Alignment.CenterEnd){
                        if(day.second.isNotEmpty()){
                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                                Text("${day.second[day.second.size/2].main.temp_min}°С", style = TextStyle(
                                    fontFamily = textStyle.fontFamily,
                                    color = Color(111,121,118),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontStyle = textStyle.fontStyle
                                )
                                )
                                Text("${day.second[day.second.size/2].main.temp_max}°С", style = TextStyle(
                                    fontFamily = textStyle.fontFamily,
                                    color = primaryColor,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontStyle = textStyle.fontStyle
                                )
                                )
                                Image(painter = painterResource(
                                    if(day.second[day.second.size/2].main.humidity>80) R.drawable.rain
                                    else if (day.second[day.second.size/2].clouds.all>10) R.drawable.cloud
                                    else R.drawable.sunpng
                                ),
                                    contentDescription = "cloud",
                                    modifier = Modifier.size(20.dp))
                            }
                        }
                    }
                }
            }
        }
    }
}