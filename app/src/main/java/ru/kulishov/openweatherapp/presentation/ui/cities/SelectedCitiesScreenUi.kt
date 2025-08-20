package ru.kulishov.openweatherapp.presentation.ui.cities

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import retrofit2.Retrofit
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.usecase.weather.GetCityWeatherByNameUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.InsertCityWeatherUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.UpdateCityWeatherUseCase
import ru.kulishov.openweatherapp.presentation.ui.components.city.CityCardUI
import ru.kulishov.openweatherapp.presentation.ui.components.city.FindCitiesField
import ru.kulishov.openweatherapp.presentation.viewmodel.cities.CitiesScreenViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.cities.CitySearchViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.CityWeatherViewModel

@Composable
fun SelectedCityScreen(
    selectedCityViewModel: CitiesScreenViewModel,
    searchViewModel: CitySearchViewModel,
    getCityWeatherByNameUseCase: GetCityWeatherByNameUseCase,
    updateCityWeatherUseCase: UpdateCityWeatherUseCase,
    insertCityWeatherUseCase: InsertCityWeatherUseCase,
    primaryColor: Color,
    textStyle: TextStyle,
    retrofit: Retrofit,

){
    val towns = selectedCityViewModel.selectedCities.collectAsState()
    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(25.dp),
        horizontalAlignment = Alignment.CenterHorizontally) {
        FindCitiesField(searchViewModel,Color.White,Color.White, TextStyle(),
            { city->
                    selectedCityViewModel.insertSelectedCities(city)
            })
        //CityCardUI(cityWeatherViewModel,Color.White, TextStyle())
        LazyColumn(modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(15.dp)) {
            items(towns.value){town->
                val cityWeatherViewModel= CityWeatherViewModel(
                    getCityWeatherByNameUseCase,
                    updateCityWeatherUseCase,
                    insertCityWeatherUseCase,
                    town,
                    retrofit
                )
                CityCardUI(cityWeatherViewModel,primaryColor, textStyle,{
                        selectedCityViewModel.deleteSelectedCities(town)
                })
            }
            item {
                Button(onClick = {
                    selectedCityViewModel.deleteSelectedCities(SelectedCity(1,"Москва","Moscow"))
                }) { }
            }
        }
    }
}