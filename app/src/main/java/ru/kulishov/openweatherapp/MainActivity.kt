package ru.kulishov.openweatherapp

import android.annotation.SuppressLint
import android.media.Image
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.kulishov.openweatherapp.data.local.database.getDatabaseBuilder
import ru.kulishov.openweatherapp.data.local.database.getRoomDatabase
import ru.kulishov.openweatherapp.data.repository.CityRepositoryImpl
import ru.kulishov.openweatherapp.data.repository.CityWeatherRepositoryImpl
import ru.kulishov.openweatherapp.data.repository.SelectedCityRepositoryImpl
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.usecase.cities.DeleteSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.FindCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.GetSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.InsertSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.GetCityWeatherByNameUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.InsertCityWeatherUseCase
import ru.kulishov.openweatherapp.domain.usecase.weather.UpdateCityWeatherUseCase
import ru.kulishov.openweatherapp.presentation.ui.cities.SelectedCityScreen
import ru.kulishov.openweatherapp.presentation.ui.components.weather.CityWeatherUI
import ru.kulishov.openweatherapp.presentation.ui.weather.WeatherScreenUi
import ru.kulishov.openweatherapp.presentation.viewmodel.cities.CitiesScreenViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.cities.CitySearchViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.CityWeatherViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.weather.WeatherNavigationViewModel
import ru.kulishov.openweatherapp.ui.theme.OpenWeatherAppTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("ViewModelConstructorInComposable")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {


            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/")
                .client(OkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()

                val db = getRoomDatabase(getDatabaseBuilder(this))
                val cityRepository= CityRepositoryImpl(db.cityDao())
                val findCityUseCase = FindCityUseCase(cityRepository)
                val searchViewModel= CitySearchViewModel(findCityUseCase)

            val cityWeatherRepository = CityWeatherRepositoryImpl(db.cityWeatherDao())
            val getCityWeatherByNameUseCase = GetCityWeatherByNameUseCase(cityWeatherRepository)
            val updateCityWeatherUseCase= UpdateCityWeatherUseCase(cityWeatherRepository)
            val insertWeatherByNameUseCase = InsertCityWeatherUseCase(cityWeatherRepository)

            val weatherScreenWeatherViewModel = CityWeatherViewModel(getCityWeatherByNameUseCase,updateCityWeatherUseCase,insertWeatherByNameUseCase,retrofit)

            val selectedCityRepository= SelectedCityRepositoryImpl(db.selectedCityDao())
            val getSelectedCityUseCase= GetSelectedCityUseCase(selectedCityRepository)
            val insertSelectedCityUseCase = InsertSelectedCityUseCase(selectedCityRepository)
            val deleteSelectedCityUseCase = DeleteSelectedCityUseCase(selectedCityRepository)
            val selectedCityScreenViewModel= CitiesScreenViewModel(getSelectedCityUseCase,
                insertSelectedCityUseCase,
                deleteSelectedCityUseCase)
            val weatherNavigationViewModel = WeatherNavigationViewModel(getSelectedCityUseCase)

            var navState by remember { mutableStateOf(true)  }
            OpenWeatherAppTheme {
                Box(Modifier.fillMaxSize().background(MaterialTheme.colorScheme.surface),
                    contentAlignment = Alignment.Center){
                    Column(
                        modifier = Modifier.padding(top=50.dp,start=25.dp,end=25.dp),
                        verticalArrangement = Arrangement.spacedBy(25.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {

                        //Image(painter = painterResource(R.drawable.sun), contentDescription = "")
                        //CityWeatherUI(cityWeatherViewModel,Color.White, TextStyle())
                        if(navState){
                            WeatherScreenUi(weatherNavigationViewModel,weatherScreenWeatherViewModel,retrofit,
                                MaterialTheme.colorScheme.onSurface,
                                MaterialTheme.typography.bodyMedium)
                        }else{
                            SelectedCityScreen(selectedCityScreenViewModel,searchViewModel,getCityWeatherByNameUseCase,
                                updateCityWeatherUseCase,insertWeatherByNameUseCase,MaterialTheme.colorScheme.onSurface,
                                MaterialTheme.typography.bodyMedium,retrofit,{navState=true})
                        }

                    }

                }
                if(navState){
                    Box(Modifier.padding(top=50.dp,start=25.dp).clickable{
                        navState=false
                    }){
                        Icon(painter = painterResource(R.drawable.menu),
                            contentDescription = "Menu",
                            tint = MaterialTheme.colorScheme.onSurface)
                    }
                }
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