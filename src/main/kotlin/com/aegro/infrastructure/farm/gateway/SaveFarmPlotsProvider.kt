package com.aegro.infrastructure.farm.gateway

import com.aegro.domain.farm.gateway.outbound.SaveFarmPlotsOutbound
import com.aegro.domain.farm.model.Plot
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepository
import com.aegro.infrastructure.farm.gateway.mongo.entity.PlotEntity
import org.springframework.stereotype.Component

@Component
class SaveFarmPlotsProvider(
        private val farmRepository: FarmRepository,
) : SaveFarmPlotsOutbound {

    override suspend fun execute(farmId: String, plots: List<Plot>) =
            farmRepository.addToSetFarmPlots(
                    farmId,
                    plots.map { PlotEntity.fromDomain(it) }
            )

}