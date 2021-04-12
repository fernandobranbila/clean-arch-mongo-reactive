package com.aegro.domain.farm.usecase

import com.aegro.domain.MockUtil
import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.outbound.FindFarmByIdOutbound
import com.aegro.domain.farm.model.Plot
import com.aegro.domain.result.model.Success
import com.aegro.domain.result.model.orThrow
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class CalculateAllTimePlotHarvestProductivityTest {

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var findFarmByIdOutbound: FindFarmByIdOutbound

    @Test
    fun `calculate successfully`() = runBlockingTest {
        val plotQuantity = 3
        val farm = MockUtil.createFarmMock().copy(plots = MockUtil.createPlotsMock(plotQuantity, harvestQuantity = 10))
        val endDate = null
        val startDate = null
        val selectedPlot = farm.plots?.get((0 until plotQuantity).random())!!
        val target = CalculateAllTimePlotHarvestProductivity(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        val result = target.execute(farm.id!!, selectedPlot.id!!, startDate, endDate).orThrow()
        val expectedResult = findAllTimePlotHarvestsProductivity(farm.plots, selectedPlot.id!!)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `fail due no productivity`() = runBlockingTest {
        val plotQuantity = 3
        val farm = MockUtil.createFarmMock().copy(plots = MockUtil.createPlotsMock(plotQuantity, harvestQuantity = 0))
        val endDate = null
        val startDate = null
        val selectedPlot = farm.plots?.get((0 until plotQuantity).random())!!
        val target = CalculateAllTimePlotHarvestProductivity(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        org.junit.jupiter.api.assertThrows<NotFoundException> {
            target.execute(farm.id!!, selectedPlot.id!!, startDate, endDate).orThrow()
        }
    }

    private fun findAllTimePlotHarvestsProductivity(plots: List<Plot>?, plotId: String) =
        plots?.find { it.id == plotId }
            ?.harvests?.map {
                it.productivity
            }?.reduceOrNull { acc, i -> acc + i }
}