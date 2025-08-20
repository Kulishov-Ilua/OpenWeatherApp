package ru.kulishov.openweatherapp.presentation.ui.components.app

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

@Composable
fun ErrorMessageBoxUI(message: String, textStyle: TextStyle){
    Box(
        Modifier.background(Color(105,0,5), RoundedCornerShape(5)),
        contentAlignment = Alignment.Center
    ){
        Text(message,
             style = TextStyle(
                fontFamily = textStyle.fontFamily,
                color = Color.White,
                fontWeight = FontWeight.Medium,
                fontSize = 12.sp,
                fontStyle = textStyle.fontStyle
            ))
    }
}