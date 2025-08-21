package ru.kulishov.openweatherapp.presentation.viewmodel.cities

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.usecase.cities.FindCityUseCase
import ru.kulishov.openweatherapp.presentation.viewmodel.BaseViewModel

class CitySearchViewModel (
    val findCityUseCase: FindCityUseCase
): BaseViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val debouncePeriod = 300L
    private val _findName = MutableStateFlow<String>("")
    val findName: StateFlow<String> = _findName.asStateFlow()

    private val _findCities = MutableStateFlow<List<SelectedCity>>(emptyList())
    val findCities: StateFlow<List<SelectedCity>> = _findCities.asStateFlow()

    fun setName(name:String){
        _findName.value=name
       launch {
           delay(300)
           if(_findName.value==name) {
               findCityUseCase(name).catch { e ->
                   _uiState.value = UiState.Error(e.message ?: "Unknow error")
               }
                   .collect { cities ->
                       _findCities.value = cities
                       _uiState.value = UiState.Success
                   }
           }
       }



    }
    fun search(name:String) {
        launch {
            delay(300)
            if (_findName.value == name) {
                findCityUseCase(name).catch { e ->
                    _uiState.value = UiState.Error(e.message ?: "Unknow error")
                }
                    .collect { cities ->

                        _findCities.value = cities
                        _uiState.value = UiState.Success
                    }
            }
        }
    }




    sealed class UiState{
        object Loading: UiState()
        object Success: UiState()
        data class Error(val message: String) : UiState()
    }
}