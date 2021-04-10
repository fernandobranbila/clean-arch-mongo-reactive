package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.farm.model.Harvest
import com.aegro.domain.farm.model.Plot

data class PlotResponseDto(
        val id: String,
        val description: String?,
        val area: Float,
        val harvests: List<HarvestResponseDto>?
){

    companion object{

        fun fromDomain(plot: Plot) =
                PlotResponseDto(
                        id = plot.id!!,
                        description = plot.description,
                        area = plot.area,
                        harvests = plot.harvests?.map { HarvestResponseDto.fromDomain(it) }
                )
    }

}
