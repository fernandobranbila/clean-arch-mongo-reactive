package com.aegro.domain.farm.gateway.inbound

import com.aegro.domain.result.model.Result

interface CalculateFarmHarvestsProductivityInbound {

    suspend fun execute(farmId: String): Result<Int, Exception>
}