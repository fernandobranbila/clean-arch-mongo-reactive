package com.aegro.domain.farm.usecase

import com.aegro.domain.farm.gateway.inbound.FindFarmByIdInbound
import com.aegro.domain.farm.gateway.outbound.FindFarmIdOutbound
import com.aegro.domain.farm.model.Farm
import com.aegro.domain.result.model.Result
import javax.inject.Named

@Named
class FindFarmById(
    private val findFarmIdOutbound: FindFarmIdOutbound
) : FindFarmByIdInbound {

    override suspend fun execute(id: String): Result<Farm, Exception> {
        val a = findFarmIdOutbound.execute(id)
        return findFarmIdOutbound.execute(id)
    }

}