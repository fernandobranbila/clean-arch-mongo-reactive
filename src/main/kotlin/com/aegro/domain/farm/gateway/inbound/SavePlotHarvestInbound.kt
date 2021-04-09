package com.aegro.domain.farm.gateway.inbound

import com.aegro.domain.farm.model.Harvest

interface SavePlotHarvestInbound {

    suspend fun execute(farmId: String, plotId: String, harvests: List<Harvest>)
}