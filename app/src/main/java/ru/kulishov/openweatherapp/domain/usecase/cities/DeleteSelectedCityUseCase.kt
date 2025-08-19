package ru.kulishov.openweatherapp.domain.usecase.cities

import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.SelectedCityRepository

class DeleteSelectedCityUseCase(
    private val repository: SelectedCityRepository
) {
    suspend operator fun invoke(city: SelectedCity) = repository.deleteSelectedCity(city)
}