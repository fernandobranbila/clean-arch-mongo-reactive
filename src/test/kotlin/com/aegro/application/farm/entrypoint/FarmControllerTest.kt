package com.aegro.application.farm.entrypoint

import com.aegro.application.MockUtil.Companion.createFarmDtoRequestMock
import com.aegro.application.MockUtil.Companion.createPlotDtoRequestMock
import com.aegro.application.farm.entrypoint.dto.FarmResponseDto
import com.aegro.application.farm.entrypoint.dto.PlotRequestDto
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepository
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepositoryCustomImpl
import com.aegro.infrastructure.farm.gateway.mongo.entity.FarmEntity
import com.aegro.infrastructure.farm.gateway.mongo.entity.PlotEntity
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.reactive.awaitFirst
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.TestCoroutineScope
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.kotlin.core.publisher.toMono
import java.util.NoSuchElementException


@ExperimentalCoroutinesApi
@SpringBootTest
@AutoConfigureWebTestClient
class FarmControllerTest {


    @MockBean
    private lateinit var farmRepository: FarmRepository

    @MockBean
    private lateinit var farmRepositoryCustom: FarmRepositoryCustomImpl

    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun findFarmById() = runBlockingTest {
        whenever(farmRepository.findById("1")).thenReturn(FarmEntity("1", "teste", null).toMono())

        webClient.get()
            .uri("/v1/farms/1")
            .exchange()
            .expectStatus().isOk
    }

    @Test
    fun findFarmByIdNotFound() = runBlockingTest {
        whenever(farmRepository.findById("1")).thenThrow(NoSuchElementException())

        webClient.get()
            .uri("/v1/farms/1")
            .exchange()
            .expectStatus().isNotFound;
    }

    @Test
    fun saveFarm() = runBlockingTest {
        val farmDtoRequest = createFarmDtoRequestMock()
        val farmEntityToBeSaved = FarmEntity.fromDomain(farmDtoRequest.toDomain())
        whenever(
            farmRepository.save(
                farmEntityToBeSaved
            )
        ).thenReturn(farmEntityToBeSaved.copy(id = "savedTest").toMono())

        webClient.post()
            .uri("/v1/farms")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(farmDtoRequest))
            .exchange()
            .expectStatus().isCreated
            .expectBody(FarmResponseDto::class.java)
    }

    @Test
    fun savePlots() = runBlockingTest {
        val farmId = "1"
        val plotRequestDto = createPlotDtoRequestMock()

        webClient.post()
            .uri("/v1/farms/$farmId/plots")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(plotRequestDto))
            .exchange()
            .expectStatus().isNoContent
    }
    
}