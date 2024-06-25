package com.ircl.cambiosmvd.domain.use_case

import com.ircl.cambiosmvd.data.remote.response.Exchange
import com.ircl.cambiosmvd.data.remote.response.Rate
import com.ircl.cambiosmvd.domain.repository.ExchangeRepository
import com.ircl.cambiosmvd.domain.repository.RatesRepository
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

class GetRatesForExchangeUseCaseTest {

    private lateinit var getRatesForExchangeUseCase: GetRatesForExchangeUseCase
    private val ratesRepository: RatesRepository = mock(RatesRepository::class.java)

    @Before
    fun setup() {
        getRatesForExchangeUseCase = GetRatesForExchangeUseCase(ratesRepository)
    }

    @Test
    fun `invoke returns rates for multiple exchanges when repository returns success`() = runTest {
        val exchangeIds = listOf(1, 2)
        val ratesList1 = listOf(Rate(id = 1, exchangeId = 1, currency = "USD", buy = 1.0, sell = 0.9, createdAt = "2022-01-01T00:00:00Z"))
        val ratesList2 = listOf(Rate(id = 2, exchangeId = 2, currency = "EUR", buy = 0.85, sell = 0.75, createdAt = "2022-01-01T00:00:00Z"))

        `when`(ratesRepository.getRatesForExchange(exchangeIds[0])).thenReturn(flowOf(Resource.Success(ratesList1)))
        `when`(ratesRepository.getRatesForExchange(exchangeIds[1])).thenReturn(flowOf(Resource.Success(ratesList2)))

        val result1 = getRatesForExchangeUseCase.invoke(exchangeIds[0]).first()
        val result2 = getRatesForExchangeUseCase.invoke(exchangeIds[1]).first()

        assertTrue(result1 is Resource.Success)
        assertEquals(ratesList1, (result1 as Resource.Success).data)

        assertTrue(result2 is Resource.Success)
        assertEquals(ratesList2, (result2 as Resource.Success).data)
    }

    @Test
    fun `invoke returns error when repository returns error`() = runTest {
        val exchangeId = 1
        val errorMessage = "Error occurred"
        `when`(ratesRepository.getRatesForExchange(exchangeId)).thenReturn(flowOf(Resource.Error(errorMessage)))

        val result = getRatesForExchangeUseCase.invoke(exchangeId).first()

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }

    @Test
    fun `invoke returns loading when repository returns loading`() = runTest {
        val exchangeId = 1
        `when`(ratesRepository.getRatesForExchange(exchangeId)).thenReturn(flowOf(Resource.Loading()))

        val result = getRatesForExchangeUseCase.invoke(exchangeId).first()

        assertTrue(result is Resource.Loading)
    }

}