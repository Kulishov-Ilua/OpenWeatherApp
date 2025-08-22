package ru.kulishov.openweatherapp.domain.usecase.cities

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.SelectedCityRepository
import javax.inject.Inject

class InsertSelectedCityUseCase @Inject constructor(
    private val repository: SelectedCityRepository
) {
    suspend operator fun invoke(city: SelectedCity): Long = repository.insertSelectedCity(city)
}