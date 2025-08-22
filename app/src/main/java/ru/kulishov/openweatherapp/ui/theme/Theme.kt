package ru.kulishov.openweatherapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

private val DarkColorScheme = darkColorScheme(
    primary = Purple80,
    secondary = PurpleGrey80,
    tertiary = Pink80,
    background = Black1,
    surface = Black1,
    onBackground = Color.White,
    onSurface = Color.White
)

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color(247, 250, 248),
    surface = Color(247, 250, 248),
    onBackground = Black1,
    onSurface = Black1
)

@Composable
fun OpenWeatherAppTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme =
            if (isSystemInDarkTheme()) DarkColorScheme
            else LightColorScheme,
        content = content,
        typography = Typography(
            titleMedium = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp,
            ),
            titleLarge = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 48.sp
            )
        )
    )

}

