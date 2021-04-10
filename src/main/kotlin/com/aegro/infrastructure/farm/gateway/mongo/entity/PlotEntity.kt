package com.aegro.infrastructure.farm.gateway.mongo.entity

import com.aegro.domain.farm.model.Harvest
import com.aegro.domain.farm.model.Plot
import org.bson.types.ObjectId

data class PlotEntity(
        val id: String,
        val description: String?,
        val area: Float,
        val harvests: List<HarvestEntity>?,
) {

    companion object {
        fun fromDomain(plot: Plot) =
                PlotEntity(
                        id = plot.id ?: ObjectId().toString(),
                        description = plot.description,
                        area = plot.area,
                        harvests = plot.harvests?.map { HarvestEntity.fromDomain(it) }
                )
    }

    fun toDomain() =
            Plot(
                    id = id,
                    description = description,
                    area = area,
                    harvests = harvests?.map { it.toDomain() }
            )
}