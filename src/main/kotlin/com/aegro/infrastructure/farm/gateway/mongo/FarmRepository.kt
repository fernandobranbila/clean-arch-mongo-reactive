package com.aegro.infrastructure.farm.gateway.mongo

import com.aegro.infrastructure.farm.gateway.mongo.entity.FarmEntity
import org.springframework.data.mongodb.repository.ReactiveMongoRepository

interface FarmRepository: ReactiveMongoRepository<FarmEntity, String>, FarmRepositoryCustom {
}