package ru.kulishov.openweatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kulishov.openweatherapp.data.local.dao.SelectedCityDao
import ru.kulishov.openweatherapp.data.local.mapper.SelectedCityMapper
import ru.kulishov.openweatherapp.data.remote.OpenWeatherApi
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.SelectedCityRepository


class SelectedCityRepositoryImpl (
    private val selectedCityDao: SelectedCityDao,
): SelectedCityRepository {
    override fun getSelectedCity(): Flow<List<SelectedCity>>  =selectedCityDao.getSelectedCities().map { entiies -> entiies.map { entity -> SelectedCityMapper.toDomain(entity) } }

    override suspend fun insertSelectedCity(city: SelectedCity): Long {
        return selectedCityDao.insertSelectedCities(SelectedCityMapper.toEntity(city))
    }

    override suspend fun deleteSelectedCity(city: SelectedCity) {
        selectedCityDao.deleteSelectedCity(SelectedCityMapper.toEntity(city))
    }
}