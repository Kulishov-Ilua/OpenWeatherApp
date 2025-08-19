package ru.kulishov.openweatherapp.data.local.mapper

import ru.kulishov.openweatherapp.data.local.entity.SelectedCityEntity
import ru.kulishov.openweatherapp.domain.model.SelectedCity

object SelectedCityMapper {
    fun toDomain(entity: SelectedCityEntity): SelectedCity = SelectedCity(
        id = entity.id,
        localName = entity.localName,
        enName = entity.enName
    )


    fun toEntity(domain: SelectedCity): SelectedCityEntity = SelectedCityEntity(
        id = domain.id,
        localName = domain.localName,
        enName = domain.enName
    )
}