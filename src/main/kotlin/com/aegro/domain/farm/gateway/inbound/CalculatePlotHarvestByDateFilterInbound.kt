package com.aegro.domain.farm.gateway.inbound

import java.time.LocalDate

interface CalculatePlotHarvestByDateFilterInbound {

    suspend fun execute(farmId: String, plotId: String, startDate: LocalDate?, endDate: LocalDate?)
}