package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.farm.model.Harvest
import com.aegro.domain.farm.model.Plot

data class PlotRequestDto(
        val id: String?,
        val area: Long,
        val description: String? = null,
        val harvests: List<Harvest>? = null
){

    fun toDomain() =
            Plot(
                    id = id,
                    area = area,
                    description = description,
                    harvests = harvests
            )
}
