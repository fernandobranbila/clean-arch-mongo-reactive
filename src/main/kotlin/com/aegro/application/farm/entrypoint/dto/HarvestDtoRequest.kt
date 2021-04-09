package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.farm.model.Harvest

data class HarvestDtoRequest(
        val productivity: Int,
        val description: String
){

    fun toDomain() =
            Harvest(
                   id = null,
                   productivity = productivity,
                   description = description
            )
}
