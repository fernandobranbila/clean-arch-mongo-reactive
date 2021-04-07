package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.FindFarmByIdInbound
import com.aegro.domain.farm.gateway.outbound.FindFarmIdOutbound
import com.aegro.domain.farm.model.Farm
import javax.inject.Named

@Named
class FindFarmById(
    private val findFarmIdOutbound: FindFarmIdOutbound
) : FindFarmByIdInbound {

    override suspend fun execute(id: String) =
            findFarmIdOutbound.execute(id)
}