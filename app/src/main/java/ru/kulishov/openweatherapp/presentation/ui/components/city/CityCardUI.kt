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
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.CityWeatherViewModel

@Composable
fun CityCardUI(viewModel: CityWeatherViewModel,
               primaryColor: Color,
               textStyle: TextStyle,
               onTap:()-> Unit){
    val forecast = viewModel.currentForecast.collectAsState()
    val dtText = viewModel.currentForecast.collectAsState()


    Box(Modifier.fillMaxWidth().height(60.dp), contentAlignment = Alignment.Center) {
        Image(painter = painterResource(R.drawable.city_card_bacground),
            contentDescription = "backgroung",
            Modifier.fillMaxSize()
        )
        Row(verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(start=25.dp,end=25.dp).fillMaxSize(),
            horizontalArrangement = Arrangement.Start){
            Icon(painter = painterResource(R.drawable.exit),
                contentDescription = "delete",
                tint = primaryColor,
                modifier = Modifier.clickable{
                    onTap()
                })
            Text(viewModel.cityName.localName, style = TextStyle(
                fontFamily = textStyle.fontFamily,
                color = primaryColor,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                fontStyle = textStyle.fontStyle
            ),
                modifier = Modifier.padding(start=15.dp))
            Box(Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd){
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(15.dp)) {
                    if(dtText.value.dt_txt=="") {
                        Text("Not data",style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        ))
                    }else{
                        Text("${forecast.value.main.temp}°С", style = TextStyle(
                            fontFamily = textStyle.fontFamily,
                            color = primaryColor,
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp,
                            fontStyle = textStyle.fontStyle
                        ))


                    }


                }
            }
        }

    }
}