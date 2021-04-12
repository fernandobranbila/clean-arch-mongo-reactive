package com.aegro.domain.farm.usecase

import com.aegro.domain.MockUtil
import com.aegro.domain.MockUtil.Companion.createPlotsMock
import com.aegro.domain.farm.gateway.outbound.SaveFarmOutbound
import com.aegro.domain.farm.gateway.outbound.SaveFarmPlotsOutbound
import com.nhaarman.mockitokotlin2.doNothing
import com.nhaarman.mockitokotlin2.times
import com.nhaarman.mockitokotlin2.verify
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class SaveFarmPlotsTest {

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var saveFarmPlotsOutbound: SaveFarmPlotsOutbound

    @Test
    fun saveSuccess() = runBlockingTest {
        val farm = MockUtil.createFarmMock()
        val plots = createPlotsMock()
        val target = SaveFarmPlots(saveFarmPlotsOutbound)
        target.execute(farm.id!!, plots)
        verify(saveFarmPlotsOutbound, times(1)).execute(farm.id!!, plots)
    }
}