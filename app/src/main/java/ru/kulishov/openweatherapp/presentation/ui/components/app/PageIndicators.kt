package ru.kulishov.openweatherapp.presentation.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import ru.kulishov.openweatherapp.R

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPageIndex: Int,
    primaryColor: Color,
    modifier: Modifier = Modifier
) {
    Box() {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .padding(bottom = 8.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            repeat(pageCount) { iteration ->
                val color = if (currentPageIndex == iteration) primaryColor else Color.Transparent
                if (iteration == 0) {
                    if (currentPageIndex == iteration) {
                        Icon(
                            painter = painterResource(R.drawable.location),
                            contentDescription = "geo",
                            tint = primaryColor,
                            modifier = Modifier.padding(5.dp)
                        )
                    } else {
                        Icon(
                            painter = painterResource(R.drawable.empty_location),
                            contentDescription = "geo",
                            tint = primaryColor,
                            modifier = Modifier.padding(5.dp)
                        )
                    }

                } else {
                    Box(
                        modifier = modifier
                            .padding(5.dp)
                            .clip(RoundedCornerShape(10))
                            .background(color)
                            .border(color = primaryColor, width = 1.dp)
                            .size(12.dp)
                    )
                }

            }
        }
    }
}