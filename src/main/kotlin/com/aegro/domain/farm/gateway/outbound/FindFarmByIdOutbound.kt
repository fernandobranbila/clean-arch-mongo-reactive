package com.aegro.domain.farm.gateway.outbound

import com.aegro.domain.result.model.Result
import com.aegro.domain.farm.model.Farm

interface FindFarmByIdOutbound {

    suspend fun execute(id: String): Result<Farm, Exception>

}