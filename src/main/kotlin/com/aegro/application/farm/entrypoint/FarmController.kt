package com.aegro.application.farm.entrypoint

import com.aegro.application.farm.entrypoint.dto.*
import com.aegro.domain.farm.gateway.inbound.*
import com.aegro.domain.farm.model.PlotHarvestDateTypeFilter
import com.aegro.domain.result.model.orThrow
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.http.HttpEntity
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import java.time.LocalDate

@RestController
@RequestMapping("/v1/farms")
class FarmController(
        private val saveFarmInbound: SaveFarmInbound,
        private val findFarmInbound: FindFarmByIdInbound,
        private val saveFarmPlotsInbound: SaveFarmPlotsInbound,
        private val savePlotHarvestInbound: SavePlotHarvestInbound,
        private val calculatePlotHarvestByDateFilterInbound: CalculatePlotHarvestByDateFilterInbound,
) {

    @GetMapping("/{id}")
    suspend fun findFarmById(@PathVariable id: String) =
            FarmResponseDto.fromDomain(findFarmInbound.execute(id).orThrow())

    @PostMapping
    suspend fun saveFarm(@RequestBody farmRequestDto: FarmRequestDto) =
            FarmResponseDto.fromDomain(saveFarmInbound.execute(farmRequestDto.toDomain()))


    @PostMapping("/{farmId}/plots")
    suspend fun savePlots(
            @PathVariable farmId: String,
            @RequestBody plotRequestDto: List<PlotRequestDto>,
    ): HttpEntity<HttpStatus> {
        saveFarmPlotsInbound.execute(farmId, plotRequestDto.map { it.toDomain() })
        return HttpEntity(HttpStatus.NO_CONTENT)
    }

    @PostMapping("/{farmId}/plots/{plotId}/harvests")
    suspend fun savePlotHarvest(
            @PathVariable farmId: String,
            @PathVariable plotId: String,
            @RequestBody harvestDtoRequest: List<HarvestDtoRequest>,
    ): HttpEntity<HttpStatus> {
        savePlotHarvestInbound.execute(farmId, plotId, harvestDtoRequest.map { it.toDomain() })
        return HttpEntity(HttpStatus.NO_CONTENT)
    }

    @GetMapping(value = ["/{farmId}/plots/{plotId}/harvests/productivity"])
    suspend fun calculatePlotHarvestByStartDateAndEndDate(
            @PathVariable farmId: String,
            @PathVariable plotId: String,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") startDate: LocalDate?,
            @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") endDate: LocalDate?,
    ): Int {
        return calculatePlotHarvestByDateFilterInbound.execute(
                farmId,
                plotId,
                startDate,
                endDate
        ).orThrow()
    }
/*
    @GetMapping(params = ["dateFilterType"], value = ["/{farmId}/plots/{plotId}/harvests/productivity"])
    suspend fun calculatePlotHarvestByDateType(
            @PathVariable farmId: String,
            @PathVariable plotId: String,
            @RequestParam dateFilterType: PlotHarvestDateTypeFilter,
    ) {

    }*/


}