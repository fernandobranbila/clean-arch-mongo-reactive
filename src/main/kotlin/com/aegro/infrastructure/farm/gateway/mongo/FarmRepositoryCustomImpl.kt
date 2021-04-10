package com.aegro.infrastructure.farm.gateway.mongo

import com.aegro.domain.farm.model.Plot
import com.aegro.infrastructure.farm.gateway.mongo.entity.FarmEntity
import com.aegro.infrastructure.farm.gateway.mongo.entity.HarvestEntity
import com.aegro.infrastructure.farm.gateway.mongo.entity.PlotEntity
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.data.domain.Sort
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.aggregation.Aggregation
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.mongodb.core.query.Update
import org.springframework.data.mongodb.core.updateMulti

class FarmRepositoryCustomImpl(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) : FarmRepositoryCustom {

    override suspend fun addToSetFarmPlots(farmId: String, plots: List<PlotEntity>) {
        reactiveMongoTemplate.updateMulti(
            Query.query(Criteria.where("id").`is`(farmId)),
            Update().addToSet("plots").each(plots), FarmEntity::class.java
        ).awaitFirst()
    }

    override suspend fun addToSetPlotHarvests(farmId: String, plotId: String, harvests: List<HarvestEntity>) {
        reactiveMongoTemplate.updateMulti(
            Query.query(Criteria.where("id").`is`(farmId).and("plots.id").`is`(plotId)),
            Update().addToSet("plots.\$.harvests").each(harvests), FarmEntity::class.java
        ).awaitFirst()
    }

}