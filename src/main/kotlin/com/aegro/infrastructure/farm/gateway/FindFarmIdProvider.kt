package com.aegro.infrastructure.farm.gateway

import com.aegro.domain.farm.gateway.outbound.FindFarmIdOutbound
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class FindFarmIdProvider(
    private val farmRepository: FarmRepository
) : FindFarmIdOutbound {

    override suspend fun execute(id: String) =
            farmRepository.findById(id).awaitFirst().toDomain()
}