package com.aegro.domain.farm.gateway.outbound

import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Plot

interface SaveFarmPlotsOutbound {

    suspend fun execute(farmId: String, plots: List<Plot>)
}