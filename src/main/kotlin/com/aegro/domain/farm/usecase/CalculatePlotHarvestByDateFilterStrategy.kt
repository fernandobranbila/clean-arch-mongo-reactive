package com.aegro.domain.farm.usecase

import java.time.LocalDate

interface CalculatePlotHarvestByDateFilterStrategy {

    fun validateFilter(startDate: LocalDate?, endDate: LocalDate?): Boolean

    fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?)
}