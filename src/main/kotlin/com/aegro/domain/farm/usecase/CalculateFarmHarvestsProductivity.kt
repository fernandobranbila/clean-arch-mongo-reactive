package com.aegro.domain.farm.usecase

import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.inbound.CalculateFarmHarvestsProductivityInbound
import com.aegro.domain.farm.gateway.outbound.FindFarmIdOutbound
import com.aegro.domain.farm.model.Harvest
import com.aegro.domain.farm.model.Plot
import com.aegro.domain.result.model.Failure
import com.aegro.domain.result.model.Result
import com.aegro.domain.result.model.Success
import com.aegro.domain.result.model.onFailure
import javax.inject.Named

@Named
class CalculateFarmHarvestsProductivity(
    private val findFarmIdOutbound: FindFarmIdOutbound
) : CalculateFarmHarvestsProductivityInbound {

    override suspend fun execute(farmId: String): Result<Int, Exception> {
        val (_, _, plots) = findFarmIdOutbound.execute(farmId).onFailure { return it }
        return calculateFarmProductivity(plots)
            ?.let {
                Success(it)
            } ?: Failure(NotFoundException(code = "farm.plot.harvest.not-found-productivity"))
    }

    private fun calculateFarmProductivity(plots: List<Plot>?) =
        plots?.mapNotNull {
            it.harvests
        }?.flatten()
            ?.map {
                it.productivity
            }?.reduceOrNull { acc, i -> acc + i }

}
