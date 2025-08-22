package ru.kulishov.openweatherapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.SelectedCity

interface CityRepository {
    suspend fun findCities(name: String): Flow<List<SelectedCity>>
}