package com.aegro.domain.farm.gateway.outbound

import com.aegro.domain.farm.model.Harvest

interface SavePlotHarvestOutbound {

    suspend fun execute(farmId: String, plotId: String, harvests: List<Harvest>)
}