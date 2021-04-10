package com.aegro.domain.farm.gateway.outbound

import com.aegro.domain.result.model.Result
import com.aegro.domain.farm.model.Farm

interface FindFarmIdOutbound {

    suspend fun execute(id: String): Result<Farm, Exception>

}