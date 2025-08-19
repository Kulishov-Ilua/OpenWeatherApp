package ru.kulishov.openweatherapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

import androidx.room.Room
import ru.kulishov.openweatherapp.data.local.database.AppDatabase
import ru.kulishov.openweatherapp.data.local.database.getDatabaseBuilder
import ru.kulishov.openweatherapp.data.local.database.getRoomDatabase
import ru.kulishov.openweatherapp.data.repository.CityRepositoryImpl
import ru.kulishov.openweatherapp.domain.repository.CityRepository
import ru.kulishov.openweatherapp.domain.usecase.cities.FindCityUseCase
import ru.kulishov.openweatherapp.presentation.ui.FindCitiesField
import ru.kulishov.openweatherapp.presentation.viewmodel.CitySearchViewModel
import ru.kulishov.openweatherapp.ui.theme.OpenWeatherAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            OpenWeatherAppTheme {
                val db = getRoomDatabase(getDatabaseBuilder(this))
                val cityRepository= CityRepositoryImpl(db.cityDao())
                val findCityUseCase = FindCityUseCase(cityRepository)
                val searchViewModel= CitySearchViewModel(findCityUseCase)
                FindCitiesField(searchViewModel)
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    OpenWeatherAppTheme {
        Greeting("Android")
    }
}