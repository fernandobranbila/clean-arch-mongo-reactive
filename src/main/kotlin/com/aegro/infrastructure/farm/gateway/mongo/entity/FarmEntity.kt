package com.aegro.infrastructure.farm.gateway.mongo.entity

import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Plot
import org.bson.types.ObjectId
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document("farm")
data class FarmEntity(
        @Id
        val id: String?,
        val description: String,
        val plots: List<PlotEntity>? = null,
) {

    companion object {
        suspend fun fromDomain(farm: Farm) =
                FarmEntity(
                        id = farm.id,
                        description = farm.description,
                        plots = farm.plots?.map { PlotEntity.fromDomain(it) }
                )
    }

    suspend fun toDomain() =
            Farm(
                    id = id,
                    description = description,
                    plots = plots?.map { it.toDomain() }
            )
}