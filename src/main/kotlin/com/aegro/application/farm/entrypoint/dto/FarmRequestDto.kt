package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Plot

data class FarmRequestDto(
        val description: String,
        val plots: List<PlotRequestDto>? = null
) {

    suspend fun toDomain() =
            Farm(
                    id = null,
                    description = description,
                    plots = plots?.map { it.toDomain() }
            )

}