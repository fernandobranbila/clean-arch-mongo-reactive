package com.aegro.domain.farm.usecase

import com.aegro.domain.result.model.Result
import java.time.LocalDate

interface CalculatePlotHarvestDateFilterStrategy {

    suspend fun validateFilter(startDate: LocalDate?, endDate: LocalDate?): Boolean

    suspend fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?): Result<Int, Exception>
}