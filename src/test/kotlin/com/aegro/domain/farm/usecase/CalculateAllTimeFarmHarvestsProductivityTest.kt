package com.aegro.domain.farm.usecase

import com.aegro.domain.MockUtil.Companion.createFarmMock
import com.aegro.domain.MockUtil.Companion.createPlotsMock
import com.aegro.domain.MockUtil.Companion.generateRandomLocalDate
import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.outbound.FindFarmByIdOutbound
import com.aegro.domain.farm.model.Plot
import com.aegro.domain.result.model.Success
import com.aegro.domain.result.model.orThrow
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
class CalculateAllTimeFarmHarvestsProductivityTest {

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var findFarmByIdOutbound: FindFarmByIdOutbound

    @Test
    fun `calculate successfully`() = runBlockingTest {
        val farm = createFarmMock().copy(plots = createPlotsMock(harvestQuantity = 10))
        val endDate = null
        val startDate = null
        val target = CalculateAllTimeFarmHarvestsProductivity(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        val result = target.execute(farm.id!!, startDate, endDate).orThrow()
        val expectedResult = calculateFarmProductivity(farm.plots)
        assertEquals(result, expectedResult)
    }

    @Test
    fun `fail due no productivity`() = runBlockingTest {
        val farm = createFarmMock().copy(plots = createPlotsMock(harvestQuantity = 0))
        val endDate = null
        val startDate = null
        val target = CalculateAllTimeFarmHarvestsProductivity(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        assertThrows<NotFoundException> {
            target.execute(farm.id!!, startDate, endDate).orThrow()
        }
    }

    private fun calculateFarmProductivity(plots: List<Plot>?) =
        plots?.asSequence()?.mapNotNull {
            it.harvests
        }?.flatten()
            ?.map {
                it.productivity
            }?.reduceOrNull { acc, i -> acc + i }

}