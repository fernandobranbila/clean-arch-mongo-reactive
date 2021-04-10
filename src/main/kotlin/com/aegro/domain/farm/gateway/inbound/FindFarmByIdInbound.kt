package com.aegro.domain.farm.gateway.inbound

import com.aegro.domain.farm.model.Farm
import com.aegro.domain.result.model.Result

interface FindFarmByIdInbound {

    suspend fun execute(id: String): Result<Farm, Exception>
}