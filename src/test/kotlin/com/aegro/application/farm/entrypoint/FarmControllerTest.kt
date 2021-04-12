package com.aegro.application.farm.entrypoint

import com.aegro.MockUtil.Companion.createFarmDtoRequestMock
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepository
import com.aegro.infrastructure.farm.gateway.mongo.entity.FarmEntity
import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.bson.types.ObjectId
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import reactor.kotlin.core.publisher.toMono


@ExperimentalCoroutinesApi
@SpringBootTest
@AutoConfigureWebTestClient
class FarmControllerTest {


    @MockBean
    private lateinit var farmRepository: FarmRepository


    @Autowired
    lateinit var webClient: WebTestClient

    @Test
    fun findFarmById() = runBlockingTest {
        whenever(farmRepository.findById("1")).thenReturn(FarmEntity("1", "teste", null).toMono())

        webClient.get()
            .uri("/v1/farms/1")
            .exchange()
            .expectStatus().isOk;
    }

    @Test
    fun saveFarm() = runBlockingTest {
        val farmDtoRequest = createFarmDtoRequestMock()

        whenever(farmRepository.save(FarmEntity(any(), farmDtoRequest.description, farmDtoRequest.plots)))
            .thenReturn(FarmEntity("1", "teste", null).toMono())

        webClient.post()
            .uri("/v1/farms")
            .contentType(MediaType.APPLICATION_JSON)
            .body(BodyInserters.fromValue(farmDtoRequest))
            .exchange()
            .expectStatus().isOk;
    }

    @Test
    fun savePlots() {
    }

    @Test
    fun savePlotHarvest() {
    }

    @Test
    fun calculatePlotHarvestByStartDateAndEndDate() {
    }

    @Test
    fun calculateFarmHarvestsProductivity() {
    }
}