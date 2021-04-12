package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.FindFarmByIdInbound
import com.aegro.domain.farm.gateway.outbound.FindFarmByIdOutbound
import com.aegro.domain.farm.model.Farm
import com.aegro.domain.result.model.Result
import javax.inject.Named

@Named
class FindFarmById(
    private val findFarmByIdOutbound: FindFarmByIdOutbound
) : FindFarmByIdInbound {

    override suspend fun execute(id: String): Result<Farm, Exception> {
        return findFarmByIdOutbound.execute(id)
    }

}