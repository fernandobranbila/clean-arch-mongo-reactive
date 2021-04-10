package com.aegro.domain.farm.model

import java.time.LocalDateTime

data class Harvest(
        val id: String?,
        val productivity: Int,
        val description: String,
        val dateAndTime: LocalDateTime
)
