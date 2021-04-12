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
import java.time.LocalDateTime

@OptIn(ExperimentalCoroutinesApi::class)
internal class CalculateFarmHarvestProductivityBetweenStartDateAndEndDateTest{


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
        val harvests = farm.plots?.mapNotNull { it.harvests }?.flatten()
        val endDate = harvests?.maxOf { it.dateAndTime }?.toLocalDate()
        val startDate = harvests?.minOf { it.dateAndTime }?.toLocalDate()
        val target = CalculateFarmHarvestProductivityBetweenStartDateAndEndDate(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        val result = target.execute(farm.id!!, startDate!!, endDate!!).orThrow()
        val expectedResult = calculateFarmHarvestsProductivityBetweenStartDateAndEndDate(farm.plots, startDate, endDate)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `fail due no productivity`() = runBlockingTest {
        val plotQuantity = 3
        val startDate = MockUtil.generateRandomLocalDate()
        val endDate = startDate.plusDays(2)
        val farm = MockUtil.createFarmMock().copy(plots = MockUtil.createPlotsMock(plotQuantity, harvestQuantity = 0))
        val target = CalculateFarmHarvestProductivityBetweenStartDateAndEndDate(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        org.junit.jupiter.api.assertThrows<NotFoundException> {
            target.execute(farm.id!!, startDate!!, endDate!!).orThrow()
        }
    }
    private fun calculateFarmHarvestsProductivityBetweenStartDateAndEndDate(plots: List<Plot>?, startDate: LocalDate, endDate: LocalDate) =
        plots?.asSequence()?.mapNotNull {
            it.harvests
        }?.flatten()
            ?.filter { harvest ->
                harvest.dateAndTime.toLocalDate() >= startDate && harvest.dateAndTime.toLocalDate() <= endDate
            }
            ?.map {
                it.productivity
            }?.reduceOrNull { acc, i -> acc + i }
}