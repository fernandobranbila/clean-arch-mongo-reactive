package com.aegro.infrastructure.farm.gateway.mongo.entity

import com.aegro.domain.farm.model.Harvest
import org.bson.types.ObjectId
import java.time.LocalDateTime

data class HarvestEntity(
        val id: String?,
        val productivity: Int,
        val description: String,
        val dateAndTime: LocalDateTime,
) {

    companion object {
        fun fromDomain(harvest: Harvest) =
                HarvestEntity(
                        id = harvest.id ?: ObjectId().toString(),
                        productivity = harvest.productivity,
                        description = harvest.description,
                        dateAndTime = harvest.dateAndTime
                )
    }

    fun toDomain() =
            Harvest(
                    id = id,
                    productivity = productivity,
                    description = description,
                    dateAndTime = dateAndTime
            )
}