package ru.kulishov.openweatherapp.domain.model


sealed class UiState {
    object Loading : UiState()
    object Success : UiState()
    object NotPermission : UiState()
    data class InternetError(val message: String) : UiState()
    object locationEnabled : UiState()
    data class Error(val message: String) : UiState()
}