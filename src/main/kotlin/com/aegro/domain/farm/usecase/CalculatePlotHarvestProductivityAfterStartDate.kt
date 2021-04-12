package com.aegro.domain.farm.usecase

import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.outbound.FindFarmIdOutbound
import com.aegro.domain.farm.model.Plot
import com.aegro.domain.result.model.Failure
import com.aegro.domain.result.model.Result
import com.aegro.domain.result.model.Success
import com.aegro.domain.result.model.onFailure
import java.time.LocalDate
import javax.inject.Named

@Named
class CalculatePlotHarvestProductivityAfterStartDate(
        private val findFarmIdOutbound: FindFarmIdOutbound,

) : CalculatePlotHarvestProductivityDateFilterStrategy {

    override suspend fun validateFilter(startDate: LocalDate?, endDate: LocalDate?) =
            startDate != null && endDate == null

    override suspend fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?): Result<Int, Exception> {
        val (_, _, plots) = findFarmIdOutbound.execute(farmId).onFailure { return it }
        return calculatePlotHarvestsProductivityAfterStartDate(plots, plotId, startDate)
                ?.let {
                    Success(it)
                } ?: Failure(NotFoundException(code = "farm.plot.harvest.not-found-productivity"))

    }

    private fun calculatePlotHarvestsProductivityAfterStartDate(plots: List<Plot>?, plotId: String, startDate: LocalDate?) =
            plots?.find { it.id == plotId }
                    ?.harvests?.filter { harvest ->
                        harvest.dateAndTime.toLocalDate() >= startDate
                    }?.map {
                        it.productivity
                    }?.reduceOrNull { acc, i -> acc + i }

}
