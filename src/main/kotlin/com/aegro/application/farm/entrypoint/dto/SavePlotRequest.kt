package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.farm.model.Plot

data class SavePlotRequest(
        val plots: List<Plot>
)
