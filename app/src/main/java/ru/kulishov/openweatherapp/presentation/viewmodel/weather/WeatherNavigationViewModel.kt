package ru.kulishov.openweatherapp.presentation.viewmodel.weather

import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.usecase.cities.GetSelectedCityUseCase
import ru.kulishov.openweatherapp.presentation.viewmodel.BaseViewModel
import ru.kulishov.openweatherapp.presentation.viewmodel.cities.CitiesScreenViewModel
import javax.inject.Inject

@HiltViewModel
class WeatherNavigationViewModel @Inject constructor(
    private val getSelectedCityUseCase: GetSelectedCityUseCase
) : BaseViewModel() {
    private val _uiState =
        MutableStateFlow<CitiesScreenViewModel.UiState>(CitiesScreenViewModel.UiState.Loading)
    val uiState: StateFlow<CitiesScreenViewModel.UiState> = _uiState.asStateFlow()
    private val _selectedCities = MutableStateFlow<List<SelectedCity>>(emptyList())
    val selectedCities: StateFlow<List<SelectedCity>> = _selectedCities.asStateFlow()

    private val _currentPage = MutableStateFlow<Int>(0)
    val currentPage: StateFlow<Int> = _currentPage.asStateFlow()

    private val _isSwipeBlocked = MutableStateFlow<Boolean>(false)
    val isSwipeBlocked: StateFlow<Boolean> = _isSwipeBlocked.asStateFlow()


    init {
        loadSelectedCities()
    }

    private fun loadSelectedCities() {
        launch {
            getSelectedCityUseCase()
                .catch { e ->
                    _uiState.value =
                        CitiesScreenViewModel.UiState.Error(e.message ?: "Unknow error")
                }
                .collect { cities ->
                    _selectedCities.value = cities
                    _uiState.value = CitiesScreenViewModel.UiState.Success
                }
        }
    }

    fun pageChanged(id: Int) {
        _currentPage.value = id

    }

    fun blockedSwipe() {
        launch {
            _isSwipeBlocked.value = true
            delay(300)
            _isSwipeBlocked.value = false
        }
    }

    sealed class UiState {
        object Loading : UiState()
        object Success : UiState()
        data class Error(val message: String) : UiState()
    }
}