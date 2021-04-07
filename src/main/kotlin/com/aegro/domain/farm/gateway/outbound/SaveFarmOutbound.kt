package com.aegro.domain.farm.gateway.outbound

import com.aegro.domain.farm.model.Farm

interface SaveFarmOutbound {

    suspend fun execute(farm: Farm): Farm
}