package com.aegro.application.farm.entrypoint

import com.aegro.application.farm.entrypoint.dto.*
import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.inbound.*
import com.aegro.domain.result.model.orThrow
import com.aegro.infrastructure.farm.gateway.mongo.FarmRepositoryCustomImpl
import org.slf4j.LoggerFactory
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.lang.RuntimeException
import java.time.LocalDate

@RestController
@RequestMapping("/v1/farms")
class FarmController(
    private val saveFarmInbound: SaveFarmInbound,
    private val findFarmInbound: FindFarmByIdInbound,
    private val saveFarmPlotsInbound: SaveFarmPlotsInbound,
    private val savePlotHarvestInbound: SavePlotHarvestInbound,
    private val calculatePlotHarvestProductivityByDateFilterInbound: CalculatePlotHarvestByDateFilterInbound,
    private val calculateFarmHarvestsProductivityInbound: CalculateFarmHarvestsProductivityInbound,


    private val farmRepositoryCustomImpl: FarmRepositoryCustomImpl
) {

    companion object {
        private val log = LoggerFactory.getLogger(FarmController::class.java)
    }

    @GetMapping("/{id}")
    suspend fun findFarmById(@PathVariable id: String): FarmResponseDto {
        try {
            log.info("entrando")
            return FarmResponseDto.fromDomain(findFarmInbound.execute(id).orThrow())
        } catch (e: Exception) {
            log.info("erro")
            log.info(e.message)
            log.info(e.printStackTrace().toString())
            throw NotFoundException(e.toString())
        }
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    suspend fun saveFarm(@RequestBody farmRequestDto: FarmRequestDto): FarmResponseDto {
        try {
            return FarmResponseDto.fromDomain(saveFarmInbound.execute(farmRequestDto.toDomain()))
        } catch (e: Exception) {
            log.info("erro")
            log.info(e.message)
            log.info(e.printStackTrace().toString())
            throw NotFoundException(e.toString())
        }
    }


    @PostMapping("/{farmId}/plots")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun savePlots(
        @PathVariable farmId: String,
        @RequestBody plotRequestDto: List<PlotRequestDto>,
    ) =
        saveFarmPlotsInbound.execute(farmId, plotRequestDto.map { it.toDomain() })


    @PostMapping("/{farmId}/plots/{plotId}/harvests")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    suspend fun savePlotHarvest(
        @PathVariable farmId: String,
        @PathVariable plotId: String,
        @RequestBody harvestDtoRequest: List<HarvestDtoRequest>,
    ) =
        savePlotHarvestInbound.execute(farmId, plotId, harvestDtoRequest.map
        { it.toDomain() })


    @GetMapping(value = ["/{farmId}/plots/{plotId}/harvests/productivity"])
    suspend fun calculatePlotHarvestByStartDateAndEndDate(
        @PathVariable farmId: String,
        @PathVariable plotId: String,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") startDate: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") endDate: LocalDate?,
    ): Int {
        return calculatePlotHarvestProductivityByDateFilterInbound.execute(
            farmId,
            plotId,
            startDate,
            endDate
        ).orThrow()
    }

    @GetMapping(value = ["/{farmId}/plots/harvests/productivity"])
    suspend fun calculateFarmHarvestsProductivity(
        @PathVariable farmId: String,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") startDate: LocalDate?,
        @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") endDate: LocalDate?,
    ) =
        calculateFarmHarvestsProductivityInbound.execute(farmId, startDate, endDate).orThrow()


}