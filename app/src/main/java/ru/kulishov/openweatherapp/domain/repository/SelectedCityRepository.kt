package ru.kulishov.openweatherapp.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.kulishov.openweatherapp.domain.model.SelectedCity

interface SelectedCityRepository {
     fun  getSelectedCity(): Flow<List<SelectedCity>>
     suspend fun insertSelectedCity(city: SelectedCity)
     suspend fun deleteSelectedCity(city: SelectedCity)
}