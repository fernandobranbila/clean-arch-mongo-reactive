package com.aegro.domain.farm.gateway.inbound

import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Plot

interface SaveFarmPlotsInbound {

    suspend fun execute(farmId: String, plots: List<Plot>)
}