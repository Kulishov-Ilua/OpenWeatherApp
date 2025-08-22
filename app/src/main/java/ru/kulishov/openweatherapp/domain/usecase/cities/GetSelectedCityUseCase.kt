package ru.kulishov.openweatherapp.domain.usecase.cities

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.SelectedCityRepository
import javax.inject.Inject

class GetSelectedCityUseCase @Inject constructor(
    private val repository: SelectedCityRepository
) {
    suspend operator fun invoke(): Flow<List<SelectedCity>> = repository.getSelectedCity()
}