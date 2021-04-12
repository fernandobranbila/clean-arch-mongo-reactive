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
import java.time.LocalDate

@OptIn(ExperimentalCoroutinesApi::class)
internal class CalculatePlotHarvestProductivityUntilEndDateTest {

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
        val selectedPlot = farm.plots?.get((0 until plotQuantity).random())!!
        val harvests = farm.plots?.filter { it.id == selectedPlot.id!! }?.mapNotNull { it.harvests }?.flatten()
        val endDate = harvests?.maxOf { it.dateAndTime }?.toLocalDate()
        val startDate = null
        val target = CalculatePlotHarvestProductivityUntilEndDate(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        val result = target.execute(farm.id!!, selectedPlot.id!!, startDate, endDate!!).orThrow()
        val expectedResult = calculatePlotHarvestsProductivityUntilEndDate(farm.plots, selectedPlot.id!!, endDate)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `fail due no productivity`() = runBlockingTest {
        val plotQuantity = 3
        val endDate = LocalDate.MAX
        val startDate = null
        val farm = MockUtil.createFarmMock().copy(plots = MockUtil.createPlotsMock(plotQuantity, harvestQuantity = 0))
        val selectedPlot = farm.plots?.get((0 until plotQuantity).random())!!
        val target = CalculatePlotHarvestProductivityUntilEndDate(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        org.junit.jupiter.api.assertThrows<NotFoundException> {
            target.execute(farm.id!!, selectedPlot.id!!, startDate, endDate).orThrow()
        }
    }

    private fun calculatePlotHarvestsProductivityUntilEndDate(plots: List<Plot>?, plotId: String, endDate: LocalDate?) =
        plots?.find { it.id == plotId }
            ?.harvests?.filter { harvest ->
                harvest.dateAndTime.toLocalDate() <= endDate
            }?.map {
                it.productivity
            }?.reduceOrNull { acc, i -> acc + i }

}