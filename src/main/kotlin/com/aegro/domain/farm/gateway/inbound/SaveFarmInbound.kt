package com.aegro.domain.farm.gateway.inbound

import com.aegro.domain.farm.model.Farm

interface SaveFarmInbound {

    suspend fun execute(farm: Farm): Farm
}