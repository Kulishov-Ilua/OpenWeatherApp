package ru.kulishov.openweatherapp.domain.usecase.cities

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.CityRepository
import javax.inject.Inject

class FindCityUseCase @Inject constructor(
    private val repository: CityRepository
) {
    suspend operator fun invoke(name:String): Flow<List<SelectedCity>> = repository.findCities(name)
}