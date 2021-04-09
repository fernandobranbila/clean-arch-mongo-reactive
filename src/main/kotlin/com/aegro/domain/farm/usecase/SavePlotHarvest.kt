package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.SavePlotHarvestInbound
import com.aegro.domain.farm.gateway.outbound.SavePlotHarvestOutbound
import com.aegro.domain.farm.model.Harvest
import javax.inject.Named

@Named
class SavePlotHarvest(
    private val savePlotHarvestOutbound: SavePlotHarvestOutbound
): SavePlotHarvestInbound {

    override suspend fun execute(farmId: String, plotId: String, harvests: List<Harvest>) =
            savePlotHarvestOutbound.execute(farmId, plotId, harvests)

}