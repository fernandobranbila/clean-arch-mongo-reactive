package com.aegro.domain.farm.usecase

import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.outbound.FindFarmByIdOutbound
import com.aegro.domain.farm.model.Plot
import com.aegro.domain.result.model.Failure
import com.aegro.domain.result.model.Result
import com.aegro.domain.result.model.Success
import com.aegro.domain.result.model.onFailure
import java.time.LocalDate
import javax.inject.Named

@Named
class CalculateAllTimePlotHarvestProductivity(
    private val findFarmByIdOutbound: FindFarmByIdOutbound,
): CalculatePlotHarvestProductivityDateFilterStrategy {

    override suspend fun validateFilter(startDate: LocalDate?, endDate: LocalDate?) =
        startDate == null && endDate == null

    override suspend fun execute(
        farmId: String,
        plotId: String,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Result<Int, Exception> {
        val (_, _, plots) = findFarmByIdOutbound.execute(farmId).onFailure { return it }
        return findAllTimePlotHarvestsProductivity(plots, plotId)
            ?.let {
                Success(it)
            } ?: Failure(NotFoundException(code = "farm.plot.harvest.not-found-productivity"))

    }

    private fun findAllTimePlotHarvestsProductivity(plots: List<Plot>?, plotId: String) =
        plots?.find { it.id == plotId }
            ?.harvests?.map {
                it.productivity
            }?.reduceOrNull { acc, i -> acc + i }

}