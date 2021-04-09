package com.aegro.domain.farm.model

data class Plot (
        val id: String?,
        val description: String?,
        val area: Long,
        val harvests: List<Harvest>?
){
}