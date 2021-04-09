package com.aegro.infrastructure.farm.gateway.mongo

import com.aegro.infrastructure.farm.gateway.mongo.entity.HarvestEntity
import com.aegro.infrastructure.farm.gateway.mongo.entity.PlotEntity

interface FarmRepositoryCustom {

    suspend fun addToSetFarmPlots(farmId: String, plots: List<PlotEntity>)

    suspend fun addToSetPlotHarvests(farmId: String, plotId: String, harvests: List<HarvestEntity>)
}