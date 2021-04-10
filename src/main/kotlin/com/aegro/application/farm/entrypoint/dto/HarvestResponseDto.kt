package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.farm.model.Harvest
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer
import java.time.LocalDateTime

data class HarvestResponseDto(
        val id: String,
        val productivity: Int,
        val description: String,
        @JsonSerialize(using = LocalDateTimeSerializer::class)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val dateAndTime: LocalDateTime
){

    companion object{

        fun fromDomain(harvest: Harvest) =
                HarvestResponseDto(
                        id = harvest.id!!,
                        productivity = harvest.productivity,
                        description = harvest.description,
                        dateAndTime = harvest.dateAndTime
                )
    }
}
