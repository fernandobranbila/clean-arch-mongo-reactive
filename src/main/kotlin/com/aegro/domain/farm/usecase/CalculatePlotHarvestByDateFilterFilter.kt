package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.CalculatePlotHarvestByDateFilterInbound
import java.lang.RuntimeException
import java.time.LocalDate
import javax.inject.Named

@Named
class CalculatePlotHarvestByDateFilterFilter(
    private val calculatePlotHarvestByDateFilterStrategy: List<CalculatePlotHarvestByDateFilterStrategy>
): CalculatePlotHarvestByDateFilterInbound {

    override suspend fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?) {
        calculatePlotHarvestByDateFilterStrategy.firstOrNull {
            it.validateFilter(startDate, endDate)
        }?.execute(farmId, plotId, startDate, endDate) ?: throw RuntimeException ("")
    }
}