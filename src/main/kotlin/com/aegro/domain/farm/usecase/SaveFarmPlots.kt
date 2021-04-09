package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.SaveFarmPlotsInbound
import com.aegro.domain.farm.gateway.outbound.SaveFarmPlotsOutbound
import com.aegro.domain.farm.model.Farm
import com.aegro.domain.farm.model.Plot
import javax.inject.Named

@Named
class SaveFarmPlots(
    private val saveFarmPlotsOutbound: SaveFarmPlotsOutbound
): SaveFarmPlotsInbound {

    override suspend fun execute(farmId: String, plots: List<Plot>) =
            saveFarmPlotsOutbound.execute(farmId, plots)
}