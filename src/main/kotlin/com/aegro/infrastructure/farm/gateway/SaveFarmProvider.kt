package com.aegro.infrastructure.farm.gateway

import com.aegro.domain.farm.gateway.outbound.SaveFarmOutbound
import com.aegro.domain.farm.model.Farm
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepository
import com.aegro.infrastructure.farm.gateway.mongo.entity.FarmEntity
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component

@Component
class SaveFarmProvider(
    private val  farmRepository: FarmRepository
): SaveFarmOutbound {

    override suspend fun execute(farm: Farm) =
            farmRepository.save(FarmEntity.fromDomain(farm)).awaitFirst().toDomain()
}