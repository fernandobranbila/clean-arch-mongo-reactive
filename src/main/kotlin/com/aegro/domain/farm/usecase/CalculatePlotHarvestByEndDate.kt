package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.outbound.CalculatePlotHarvestByEndDateOutbound
import java.time.LocalDate

class CalculatePlotHarvestByEndDate(
        private val calculatePlotHarvestByEndDateOutbound: CalculatePlotHarvestByEndDateOutbound
): CalculatePlotHarvestByDateFilterStrategy {

    override fun validateFilter(startDate: LocalDate?, endDate: LocalDate?) =
            startDate == null && endDate != null

    override fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?) {
        TODO("Not yet implemented")
    }

}