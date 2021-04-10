package com.aegro.infrastructure.farm.gateway

import com.aegro.domain.result.model.Failure
import com.aegro.domain.result.model.Success
import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.outbound.FindFarmIdOutbound
import com.aegro.domain.farm.model.Farm
import com.aegro.domain.result.model.Result
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepository
import kotlinx.coroutines.reactive.awaitFirst
import org.springframework.stereotype.Component
import java.util.NoSuchElementException

@Component
class FindFarmIdProvider(
        private val farmRepository: FarmRepository,
) : FindFarmIdOutbound {

    override suspend fun execute(id: String): Result<Farm, Exception> {
        return try {
            Success(farmRepository.findById(id).awaitFirst().toDomain())
        } catch (e: NoSuchElementException) {
            Failure(NotFoundException(code = "farm.not-found"))
        }
    }


}