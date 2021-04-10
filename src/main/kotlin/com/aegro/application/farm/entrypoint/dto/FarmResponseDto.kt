package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Plot

data class FarmResponseDto(
        val id: String,
        val description: String,
        val plots: List<PlotResponseDto>?
) {

    companion object{
       suspend fun fromDomain(farm: Farm) =
                FarmResponseDto(
                        id = farm.id!!,
                        description = farm.description,
                        plots = farm.plots?.map { PlotResponseDto.fromDomain(it) }
                )
    }

}