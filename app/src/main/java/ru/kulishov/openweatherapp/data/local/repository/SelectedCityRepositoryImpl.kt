package ru.kulishov.openweatherapp.data.local.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kulishov.openweatherapp.data.local.dao.SelectedCityDao
import ru.kulishov.openweatherapp.data.local.data.mapper.SelectedCityMapper
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.SelectedCityRepository
import javax.inject.Inject


class SelectedCityRepositoryImpl @Inject constructor(
    private val selectedCityDao: SelectedCityDao,
) : SelectedCityRepository {
    override fun getSelectedCity(): Flow<List<SelectedCity>> = selectedCityDao.getSelectedCities()
        .map { entiies -> entiies.map { entity -> SelectedCityMapper.toDomain(entity) } }

    override suspend fun insertSelectedCity(city: SelectedCity): Long {
        return selectedCityDao.insertSelectedCities(SelectedCityMapper.toEntity(city))
    }

    override suspend fun deleteSelectedCity(city: SelectedCity) {
        selectedCityDao.deleteSelectedCity(SelectedCityMapper.toEntity(city))
    }
}