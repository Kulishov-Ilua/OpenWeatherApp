package ru.kulishov.openweatherapp.presentation.ui

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ru.kulishov.openweatherapp.presentation.viewmodel.CitySearchViewModel

@Composable
fun FindCitiesField(
    viewModel: CitySearchViewModel
){
    val text = viewModel.findName.collectAsState()
    val cities = viewModel.findCities.collectAsState()
    println(cities)

    val boxHeight = animateDpAsState(targetValue = if(cities.value.size>0) 500.dp
    else 100.dp)

    Box(Modifier.fillMaxWidth().height(boxHeight.value)){
        Column(Modifier.fillMaxWidth(), horizontalAlignment = Alignment.CenterHorizontally) {
            TextField(
                value = text.value,
                onValueChange = { inp -> viewModel.setName(inp)}
            )
            Button(onClick = {viewModel.search(text.value)}) { }
            LazyColumn {
                items(cities.value){
                    city->
                    Text(city.localName)
                }
            }
        }

    }
}