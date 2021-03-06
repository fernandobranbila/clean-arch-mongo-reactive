package com.aegro.domain.farm.gateway.inbound

import com.aegro.domain.result.model.Result
import java.time.LocalDate

interface CalculatePlotHarvestByDateFilterInbound {

    suspend fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?): Result<Int, Exception>
}