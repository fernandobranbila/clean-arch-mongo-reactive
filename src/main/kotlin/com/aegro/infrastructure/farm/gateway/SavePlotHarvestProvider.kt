package com.aegro.infrastructure.farm.gateway

import com.aegro.domain.farm.gateway.outbound.SavePlotHarvestOutbound
import com.aegro.domain.farm.model.Harvest
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepository
import com.aegro.infrastructure.farm.gateway.mongo.entity.HarvestEntity
import org.springframework.stereotype.Component

@Component
class SavePlotHarvestProvider(
        private val farmRepository: FarmRepository,
) : SavePlotHarvestOutbound {

    override suspend fun execute(farmId: String, plotId: String, harvests: List<Harvest>) {
        farmRepository.addToSetPlotHarvests(
                farmId,
                plotId,
                harvests.map { HarvestEntity.fromDomain(it) }
        )
    }
}