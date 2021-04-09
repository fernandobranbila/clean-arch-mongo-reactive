package com.aegro.infrastructure.farm.gateway.mongo.entity

import com.aegro.domain.farm.model.Harvest
import org.bson.types.ObjectId

data class HarvestEntity(
        val id: String?,
        val productivity: Int,
        val description: String,
) {

    companion object {
        fun fromDomain(harvest: Harvest) =
                HarvestEntity(
                        id = harvest.id ?: ObjectId().toString(),
                        productivity = harvest.productivity,
                        description = harvest.description,
                )
    }

    fun toDomain() =
            Harvest(
                    id = id,
                    productivity = productivity,
                    description = description
            )
}