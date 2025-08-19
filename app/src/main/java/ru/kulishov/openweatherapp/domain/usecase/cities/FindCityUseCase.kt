package ru.kulishov.openweatherapp.domain.usecase.cities

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.CityRepository

class FindCityUseCase (
    private val repository: CityRepository
) {
    suspend operator fun invoke(name:String): Flow<List<SelectedCity>> = repository.findCities(name)
}