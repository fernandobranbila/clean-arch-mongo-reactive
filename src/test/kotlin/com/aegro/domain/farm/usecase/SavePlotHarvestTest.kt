package com.aegro.domain.farm.usecase

import com.aegro.domain.MockUtil
import com.aegro.domain.MockUtil.Companion.createFarmMock
import com.aegro.domain.MockUtil.Companion.createHarvestsMock
import com.aegro.domain.farm.gateway.outbound.SaveFarmPlotsOutbound
import com.aegro.domain.farm.gateway.outbound.SavePlotHarvestOutbound
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class SavePlotHarvestTest {

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var savePlotHarvestOutbound: SavePlotHarvestOutbound

    @Test
    fun success() = runBlocking {
        val plotQuantity = 3
        val farm = createFarmMock().copy(plots = MockUtil.createPlotsMock(plotQuantity = 3))
        val harvests = createHarvestsMock()
        val selectedPlot = farm.plots?.get((0 until plotQuantity).random())!!
        val target = SavePlotHarvest(savePlotHarvestOutbound)
        target.execute(farm.id!!, selectedPlot.id!!, harvests)
        verify(savePlotHarvestOutbound, times(1)).execute(farm.id!!, selectedPlot.id!!, harvests)
    }
}