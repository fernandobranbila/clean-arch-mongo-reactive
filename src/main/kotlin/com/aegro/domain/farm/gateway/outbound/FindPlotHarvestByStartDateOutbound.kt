package com.aegro.domain.farm.gateway.outbound

import java.time.LocalDate

interface FindPlotHarvestByStartDateOutbound {

    fun execute(farmId: String, plotId: String, startDate: LocalDate)
}