package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.SaveFarmInbound
import com.aegro.domain.farm.gateway.outbound.FindFarmByIdOutbound
import com.aegro.domain.farm.gateway.outbound.SaveFarmOutbound
import com.aegro.domain.farm.model.Farm
import javax.inject.Named

@Named
class SaveFarm(
    private val saveFarmOutbound: SaveFarmOutbound,
) : SaveFarmInbound {

    override suspend fun execute(farm: Farm) =
        saveFarmOutbound.execute(farm)

}