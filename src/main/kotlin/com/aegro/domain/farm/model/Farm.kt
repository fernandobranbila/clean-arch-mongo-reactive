package com.aegro.domain.farm.model

data class Farm (
        val id: String?,
        val description: String,
        val plots: List<Plot>? = null
) {
}