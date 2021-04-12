package com.aegro.domain.farm.usecase

import com.aegro.domain.MockUtil
import com.aegro.domain.farm.gateway.outbound.SaveFarmOutbound
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class SaveFarmTest {

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var saveFarmOutbound: SaveFarmOutbound

    @Test
    fun saveSuccess() = runBlockingTest {
        val farm = MockUtil.createFarmMock()
        whenever(saveFarmOutbound.execute(farm)).thenReturn(farm)
        val target = SaveFarm(saveFarmOutbound)
        val result = target.execute(farm)
        assertNotNull(result)

    }
}