package ru.kulishov.openweatherapp.widget_feature

import android.annotation.SuppressLint
import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.size
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import ru.kulishov.openweatherapp.R

class WeatherWidget : GlanceAppWidget() {
    override suspend fun provideGlance(context: Context, id: GlanceId) {

        provideContent {
            val prefs = context.getSharedPreferences("weather_widget", Context.MODE_PRIVATE)
            val temperature = prefs.getFloat("temp", 0f).toInt()
            val state = prefs.getInt("state", 0)
            MyContent(Pair(state, temperature))
        }
    }


    @SuppressLint("RestrictedApi")
    @Composable
    fun MyContent(weather: Pair<Int, Int>) {
        val (state, temperature) = weather

        Column(
            modifier = GlanceModifier.fillMaxSize()
                .background(androidx.glance.unit.ColorProvider(R.color.black)),
            verticalAlignment = Alignment.Vertical.CenterVertically,
            horizontalAlignment = Alignment.Horizontal.CenterHorizontally
        ) {

            Image(
                provider = ImageProvider(
                    when (state) {
                        3 -> R.drawable.rain
                        2 -> R.drawable.cloud
                        else -> R.drawable.sunpng
                    }
                ),
                contentDescription = "WeatherIcon",
                modifier = GlanceModifier.size(30.dp)
            )

            Text(
                text = "${temperature}°С",
                style = TextStyle(
                    color = androidx.glance.unit.ColorProvider(R.color.white),
                    fontSize = 24.sp
                )
            )
        }
    }
}