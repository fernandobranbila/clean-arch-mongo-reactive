package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.CalculatePlotHarvestByDateFilterInbound
import com.aegro.domain.result.model.Result
import java.time.LocalDate
import javax.inject.Named

@Named
class CalculatePlotHarvestByDateFilterFilter(
    private val calculatePlotHarvestProductivityDateFilterStrategy: List<CalculatePlotHarvestProductivityDateFilterStrategy>
): CalculatePlotHarvestByDateFilterInbound {

    override suspend fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?): Result<Int, Exception> {
       return  calculatePlotHarvestProductivityDateFilterStrategy.first {
            it.validateFilter(startDate, endDate)
        }.execute(farmId, plotId, startDate, endDate)
    }
}