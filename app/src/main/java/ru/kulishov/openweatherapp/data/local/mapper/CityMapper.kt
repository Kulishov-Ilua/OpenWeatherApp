package ru.kulishov.openweatherapp.data.local.mapper

import ru.kulishov.openweatherapp.data.local.entity.CityEntity
import ru.kulishov.openweatherapp.data.remote.model.City
import ru.kulishov.openweatherapp.domain.model.SelectedCity

object CityMapper {
    fun toDomain(entity: CityEntity): SelectedCity = SelectedCity(
        id = entity.id,
        localName = entity.localName,
        enName = entity.enName
    )

    fun toEntity(domain: SelectedCity): CityEntity = CityEntity(
        id = domain.id,
        localName = domain.localName,
        enName = domain.enName
    )
}