package com.aegro.application

import com.aegro.application.farm.entrypoint.dto.FarmRequestDto
import com.aegro.application.farm.entrypoint.dto.HarvestDtoRequest
import com.aegro.application.farm.entrypoint.dto.PlotRequestDto
import java.time.LocalDateTime


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

        fun createPlotDtoRequestMock(
            quantity: Long = 1
        ): List<PlotRequestDto> {
            val plots = mutableListOf<PlotRequestDto>()
            for(i in 1..quantity){
                plots.add(
                    PlotRequestDto(
                        description = "plot $i",
                        area = (1..10).random().toFloat(),
                        harvests = null
                    )
                )
            }
            return plots
        }

        fun createHarvestPlotDtoRequestMock(
            quantity: Long = 1
        ): List<HarvestDtoRequest> {
            val harvests = mutableListOf<HarvestDtoRequest>()
            for (i in 1..quantity){
                harvests.add(
                    HarvestDtoRequest(
                        description = "harvest $i",
                        dateAndTime = LocalDateTime.MIN,
                        productivity = (1..10).random()
                    )
                )
            }
            return harvests
        }

    }

}
