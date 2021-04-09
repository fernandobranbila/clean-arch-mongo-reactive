package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.outbound.FindPlotHarvestByStartDateOutbound
import java.time.LocalDate
import javax.inject.Named

@Named
class CalculatePlotHarvestByStartDate(
        private val findPlotHarvestByStartDateOutbound: FindPlotHarvestByStartDateOutbound
) : CalculatePlotHarvestByDateFilterStrategy {

    override fun validateFilter(startDate: LocalDate?, endDate: LocalDate?) =
            startDate != null && endDate == null

    override fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?) =
            findPlotHarvestByStartDateOutbound.execute(farmId, plotId, startDate!!)
}