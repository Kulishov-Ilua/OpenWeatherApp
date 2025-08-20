package ru.kulishov.openweatherapp.presentation.ui.components.weather

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
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
fun CurrentWeatherBlockUI(weather: Forecast,
                          primaryColor: Color,
                          textStyle: TextStyle){
    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(25.dp)
    ) {
        Image(painter = painterResource(
            if(weather.main.humidity>80) R.drawable.rain
            else if (weather.clouds.all>10) R.drawable.cloud
            else R.drawable.sunpng
        ),
            contentDescription = "WeatherIcon",
            modifier = Modifier.height(110.dp),
            contentScale = ContentScale.FillHeight)
        Column(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(5.dp)) {
            Text("${weather.main.temp}°С", style = TextStyle(
                fontFamily = textStyle.fontFamily,
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 56.sp,
                fontStyle = textStyle.fontStyle
            ))
            Text("Ощущвется как: ${weather.main.feels_like}°С", style = TextStyle(
                fontFamily = textStyle.fontFamily,
                color = primaryColor,
                fontWeight = FontWeight.Medium,
                fontSize = 16.sp,
                fontStyle = textStyle.fontStyle
            ))
        }
    }
}