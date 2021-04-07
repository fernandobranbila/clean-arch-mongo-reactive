package com.aegro.application.farm.entrypoint

import com.aegro.application.farm.entrypoint.dto.FarmRequestDto
import com.aegro.application.farm.entrypoint.dto.FarmResponseDto
import com.aegro.application.farm.entrypoint.dto.SavePlotRequest
import com.aegro.domain.farm.gateway.inbound.FindFarmByIdInbound
import com.aegro.domain.farm.gateway.inbound.SaveFarmInbound
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/farms")
class FarmController(
        private val saveFarmInbound: SaveFarmInbound,
        private val findFarmInbound: FindFarmByIdInbound,
        private val saveFarmPlots: SaveFarmPlots

        ) {

    @GetMapping("/{id}")
    suspend fun findFarmById(@PathVariable id: String) =
            FarmResponseDto.fromDomain(findFarmInbound.execute(id))

    @PostMapping
    suspend fun saveFarm(@RequestBody farmRequestDto: FarmRequestDto): FarmResponseDto {
        println("test")
        return FarmResponseDto.fromDomain(saveFarmInbound.execute(farmRequestDto.toDomain()))
    }

    @PostMapping("/{id}/plots")
    suspend fun savePlots(
            @PathVariable id: String,
            @RequestBody savePlotRequest: SavePlotRequest
    ){
        println(savePlotRequest)
    }


}