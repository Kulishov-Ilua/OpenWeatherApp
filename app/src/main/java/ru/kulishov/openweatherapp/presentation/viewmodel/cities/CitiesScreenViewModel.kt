package ru.kulishov.openweatherapp.presentation.viewmodel.cities

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.usecase.cities.DeleteSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.GetSelectedCityUseCase
import ru.kulishov.openweatherapp.domain.usecase.cities.InsertSelectedCityUseCase
import ru.kulishov.openweatherapp.presentation.viewmodel.BaseViewModel

class CitiesScreenViewModel(
    private val getSelectedCityUseCase: GetSelectedCityUseCase,
    private val insertSelectedCityUseCase: InsertSelectedCityUseCase,
    private val deleteSelectedCity: DeleteSelectedCityUseCase
): BaseViewModel() {
    private val _uiState = MutableStateFlow<UiState>(UiState.Loading)
    val uiState: StateFlow<UiState> = _uiState.asStateFlow()

    private val _selectedCities= MutableStateFlow<List<SelectedCity>>(emptyList())
    val selectedCities: StateFlow<List<SelectedCity>> = _selectedCities.asStateFlow()

    init{
        loadSelectedCities()
    }
    private fun loadSelectedCities(){
        launch {
            getSelectedCityUseCase()
                .catch { e->
                    _uiState.value = UiState.Error(e.message ?: "Unknow error")
                }
                .collect { cities-> _selectedCities.value=cities
                _uiState.value= UiState.Success
                }
        }
    }

    fun deleteSelectedCities(city: SelectedCity){
        launch {
            _uiState.value= UiState.Loading
            try {
                val containsCity = selectedCities.value.any { it.enName == city.enName }
                if(containsCity){
                    deleteSelectedCity(city)
                    _selectedCities.value= selectedCities.value - city
                }
                _uiState.value= UiState.Success
            } catch (e: Exception){
                _uiState.value = UiState.Error(e.message ?: "Failed to add event")
            }
        }
    }
    fun insertSelectedCities(city: SelectedCity){
        launch {
            _uiState.value= UiState.Loading
            try {
                val containsCity = selectedCities.value.any { it.enName == city.enName }

                if(!containsCity){
                    println("insert")
                    val a = insertSelectedCityUseCase(city)
                    println("tag:$a")
                    _selectedCities.value = selectedCities.value + city
                }
                _uiState.value= UiState.Success
            } catch (e: Exception){
                _uiState.value = UiState.Error(e.message ?: "Failed to add event")
            }
        }
    }
    sealed class UiState{
        object Loading: UiState()
        object Success: UiState()
        data class Error(val message: String) : UiState()
    }
}