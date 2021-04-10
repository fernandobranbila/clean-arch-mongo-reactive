package com.aegro.application.farm.entrypoint.dto

import com.aegro.domain.exception.BadRequestException
import com.aegro.domain.farm.model.Harvest
import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonDeserialize
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer
import java.time.LocalDateTime

data class HarvestDtoRequest(
        val productivity: Int,
        val description: String,
        @JsonDeserialize(using = LocalDateTimeDeserializer::class)
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        val dateAndTime: LocalDateTime,

        ) {

    fun toDomain() =
            Harvest(
                    id = null,
                    productivity = if (productivity > 0) productivity else throw BadRequestException(code = "farm.plot.harvest.invalid-productivity"),
                    description = description,
                    dateAndTime = dateAndTime
            )
}
