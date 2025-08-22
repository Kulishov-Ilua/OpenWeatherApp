package ru.kulishov.openweatherapp.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.kulishov.openweatherapp.data.local.dao.CityDao
import ru.kulishov.openweatherapp.data.local.mapper.CityMapper
import ru.kulishov.openweatherapp.domain.model.SelectedCity
import ru.kulishov.openweatherapp.domain.repository.CityRepository
import javax.inject.Inject


class CityRepositoryImpl @Inject constructor(
    private val cityDao: CityDao
) : CityRepository {
    override suspend fun findCities(name: String): Flow<List<SelectedCity>> {
        return cityDao.searchCities('*' + name + '*').map { entities ->
            entities.map { entity ->
                CityMapper.toDomain(entity)
            }
        }
    }
}