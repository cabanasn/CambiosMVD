package com.ircl.cambiosmvd.domain.use_case

import com.ircl.cambiosmvd.data.remote.response.Exchange
import com.ircl.cambiosmvd.domain.repository.ExchangeRepository
import com.ircl.cambiosmvd.utils.Resource
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

class GetExchangesUseCaseTest {

    private lateinit var getExchangesUseCase: GetExchangesUseCase
    private val exchangeRepository: ExchangeRepository = mock(ExchangeRepository::class.java)

    @Before
    fun setup() {
        getExchangesUseCase = GetExchangesUseCase(exchangeRepository)
    }

    @Test
    fun `invoke returns exchange list when repository returns success`() = runTest {
        val exchangeList = listOf(Exchange(id = 1, name = "name", icon = "icon"))
        `when`(exchangeRepository.getExchanges()).thenReturn(flowOf(Resource.Success(exchangeList)))

        val result = getExchangesUseCase.invoke().first()

        assertTrue(result is Resource.Success)
        assertEquals(exchangeList, (result as Resource.Success).data)
    }

    @Test
    fun `invoke returns error when repository returns error`() = runTest {
        val errorMessage = "Error occurred"
        `when`(exchangeRepository.getExchanges()).thenReturn(flowOf(Resource.Error(errorMessage)))

        val result = getExchangesUseCase.invoke().first()

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }

    @Test
    fun `invoke returns loading when repository returns loading`() = runTest {
        `when`(exchangeRepository.getExchanges()).thenReturn(flowOf(Resource.Loading()))

        val result = getExchangesUseCase.invoke().first()

        assertTrue(result is Resource.Loading)
    }

}