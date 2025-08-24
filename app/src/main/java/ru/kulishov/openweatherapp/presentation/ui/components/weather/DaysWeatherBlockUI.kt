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
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.kulishov.openweatherapp.R
import ru.kulishov.openweatherapp.data.remote.model.Forecast
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId

@Composable
fun DaysWeatherBlockUI(
    onClick: (day: Int) -> Unit,
    listForecast: List<Pair<Int, List<Forecast>>>,
    selectedDay: Int
) {
    val currentDate = LocalDateTime.now().dayOfMonth
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .height(IntrinsicSize.Min),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(5.dp)
    ) {
        for (day in listForecast) {
            var dayInWeek = ""
            if (day.second.isNotEmpty()) {
                val forecastDateTime =
                    Instant.ofEpochMilli(day.second.first().dt * 1000 - 10800000 + 60000)
                        .atZone(ZoneId.systemDefault())
                        .toLocalDateTime()
                dayInWeek = when (forecastDateTime.dayOfWeek.value) {
                    1 -> stringResource(R.string.mon)
                    2 -> stringResource(R.string.tue)
                    3 -> stringResource(R.string.wed)
                    4 -> stringResource(R.string.thu)
                    5 -> stringResource(R.string.fri)
                    6 -> stringResource(R.string.sat)
                    else -> stringResource(R.string.sun)
                }
            }
            Box(
                modifier = Modifier
                    .defaultMinSize(minHeight = 60.dp)
                    .height(IntrinsicSize.Min)
                    .width(353.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .clickable {
                        onClick(day.first)
                    },
                contentAlignment = Alignment.Center
            ) {
                if (selectedDay == day.first) {
                    Image(
                        painter = painterResource(R.drawable.city_card_bacground),
                        contentDescription = "background",
                        modifier = Modifier.fillMaxHeight(),
                        contentScale = ContentScale.FillHeight
                    )
                }
                Row(
                    modifier = Modifier
                        .padding(15.dp)
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(5.dp)
                ) {
                    Row(
                        modifier = Modifier.weight(1f),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(5.dp)
                    ) {
                        var showShortDate by remember { mutableStateOf(false) }
                        Text(
                            text = if (showShortDate && dayInWeek.isNotEmpty()) dayInWeek.first()
                                .toString()
                            else if (currentDate == day.first) stringResource(R.string.today)
                            else dayInWeek,
                            style = TextStyle(
                                fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                color = MaterialTheme.colorScheme.onSurface,
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                            ),
                            maxLines = 1,
                            overflow = TextOverflow.Clip,
                            onTextLayout = { textLayoutResult ->
                                if (textLayoutResult.hasVisualOverflow) {
                                    showShortDate = true
                                }
                            }
                        )
                        if (!showShortDate) {
                            Text(
                                text = if (day.second.isNotEmpty()) {
                                    "(${
                                        Instant.ofEpochMilli(day.second.first().dt * 1000 - 10800000 + 60000)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime().dayOfMonth
                                    }.${
                                        Instant.ofEpochMilli(day.second.first().dt * 1000 - 10800000 + 60000)
                                            .atZone(ZoneId.systemDefault())
                                            .toLocalDateTime().month.ordinal
                                    })"

                                } else "",
                                style = TextStyle(
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Medium,
                                    fontSize = 14.sp,
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                ),
                                maxLines = 1,
                                onTextLayout = { textLayoutResult ->
                                    if (textLayoutResult.hasVisualOverflow) {
                                        showShortDate = true
                                    }
                                }
                            )
                        }

                    }
                    if (day.second.isNotEmpty()) {
                        Row(
                            modifier = Modifier.wrapContentWidth(),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(5.dp)
                        ) {
                            Text(
                                "${day.second[day.second.size / 2].main.temp_min}°С",
                                style = TextStyle(
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    color = Color(111, 121, 118),
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                )
                            )
                            Text(
                                "${day.second[day.second.size / 2].main.temp_max}°С",
                                style = TextStyle(
                                    fontFamily = MaterialTheme.typography.bodyMedium.fontFamily,
                                    color = MaterialTheme.colorScheme.onSurface,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 16.sp,
                                    fontStyle = MaterialTheme.typography.bodyMedium.fontStyle
                                )
                            )
                            Image(
                                painter = painterResource(
                                    if (day.second[day.second.size / 2].main.humidity > 80) R.drawable.rain
                                    else if (day.second[day.second.size / 2].clouds.all > 10) R.drawable.cloud
                                    else R.drawable.sunpng
                                ),
                                contentDescription = "cloud",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                }
            }
        }
    }
}