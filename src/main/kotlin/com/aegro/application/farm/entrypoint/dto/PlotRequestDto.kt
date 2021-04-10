package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.exception.BadRequestException
import com.aegro.domain.farm.model.Harvest
import com.aegro.domain.farm.model.Plot

data class PlotRequestDto(
        val id: String?,
        val area: Float,
        val description: String? = null,
        val harvests: List<HarvestDtoRequest>? = null,
) {

    fun toDomain() =
            Plot(
                    id = id,
                    area = if (area > 0) area else throw BadRequestException(code = "farm.plot.invalid-area"),
                    description = description,
                    harvests = harvests?.map { it.toDomain() }
            )
}
