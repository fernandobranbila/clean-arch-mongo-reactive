package com.aegro.domain.farm.gateway.inbound

import com.aegro.domain.farm.model.Farm

interface FindFarmByIdInbound {

    suspend fun execute(id: String): Farm
}