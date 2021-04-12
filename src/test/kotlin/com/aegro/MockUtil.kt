package com.aegro

import com.aegro.application.farm.entrypoint.dto.FarmRequestDto
import com.aegro.application.farm.entrypoint.dto.PlotRequestDto
import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Plot


class MockUtil {

    companion object {
        fun createFarmDtoRequestMock(
            description: String = "teste",
            plots: List<PlotRequestDto>? = null
        ) =
            FarmRequestDto(
                description = description,
                plots = plots,
            )
    }

}
