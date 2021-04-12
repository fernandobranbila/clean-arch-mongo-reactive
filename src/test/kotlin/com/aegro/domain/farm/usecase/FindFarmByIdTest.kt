package com.aegro.domain.farm.usecase

import com.aegro.domain.MockUtil.Companion.createFarmMock
import com.aegro.domain.exception.NotFoundException
import com.aegro.domain.farm.gateway.outbound.FindFarmByIdOutbound
import com.aegro.domain.result.model.Failure
import com.aegro.domain.result.model.Success
import com.aegro.domain.result.model.orThrow
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.mockito.Mock
import org.mockito.MockitoAnnotations

@OptIn(ExperimentalCoroutinesApi::class)
internal class FindFarmByIdTest {

    @BeforeEach
    fun initMocks() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var findFarmByIdOutbound: FindFarmByIdOutbound

    @Test
    fun execute() = runBlockingTest {
        val farm = createFarmMock()
        val target = FindFarmById(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Success(farm))
        val result = target.execute(farm.id!!)
        assertNotNull(result.orThrow())
    }

    @Test
    fun failNotFound() = runBlockingTest {
        val farm = createFarmMock()
        val target = FindFarmById(findFarmByIdOutbound)
        whenever(findFarmByIdOutbound.execute(farm.id!!)).thenReturn(Failure(NotFoundException("Fail")))
        val result = target.execute(farm.id!!)
        org.junit.jupiter.api.assertThrows<NotFoundException> {
            result.orThrow()
        }
    }
}