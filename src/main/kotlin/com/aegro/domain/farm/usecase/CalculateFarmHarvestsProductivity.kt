package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.CalculateFarmHarvestsProductivityInbound
import com.aegro.domain.result.model.Result
import java.time.LocalDate
import javax.inject.Named

@Named
class CalculateFarmHarvestsProductivity(
    private val calculateFarmProductivityDateFilterStrategy: List<CalculateFarmProductivityDateFilterStrategy>
): CalculateFarmHarvestsProductivityInbound {

    override suspend fun execute(farmId: String, startDate: LocalDate?, endDate: LocalDate?): Result<Int, Exception> {
        return  calculateFarmProductivityDateFilterStrategy.first {
            it.validateFilter(startDate, endDate)
        }.execute(farmId, startDate, endDate)
    }
}