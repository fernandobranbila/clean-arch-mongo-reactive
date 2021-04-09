package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.outbound.CalculatePlotHarvestByStartDateAndEndDateOutbound
import java.time.LocalDate

class CalculatePlotHarvestByStartDateAndEndDate(
        private val calculatePlotHarvestByStartDateAndEndDateOutbound: CalculatePlotHarvestByStartDateAndEndDateOutbound
): CalculatePlotHarvestByDateFilterStrategy {

    override fun validateFilter(startDate: LocalDate?, endDate: LocalDate?) =
            startDate != null && endDate != null

    override fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?) {
        TODO("Not yet implemented")
    }

}