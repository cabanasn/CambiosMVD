package com.ircl.cambiosmvd.domain.use_case

import com.ircl.cambiosmvd.data.remote.response.Currency
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

class GetRatesByCurrencyUseCaseTest {

    private lateinit var getRatesByCurrencyUseCase: GetRatesByCurrencyUseCase
    private val ratesRepository: RatesRepository = mock(RatesRepository::class.java)

    @Before
    fun setup() {
        getRatesByCurrencyUseCase = GetRatesByCurrencyUseCase(ratesRepository)
    }

    @Test
    fun `invoke returns rates for currency when repository returns success`() = runTest {
        val currency = Currency.USD
        val ratesList = listOf(Rate(id = 1, exchangeId = 1, currency = "USD", buy = 1.0, sell = 0.9, createdAt = "2022-01-01T00:00:00Z"))

        `when`(ratesRepository.getRatesByCurrency(currency)).thenReturn(flowOf(Resource.Success(ratesList)))

        val result = getRatesByCurrencyUseCase.invoke(currency).first()

        assertTrue(result is Resource.Success)
        assertEquals(ratesList, (result as Resource.Success).data)
    }

    @Test
    fun `invoke returns only rates for specified currency when repository returns success`() = runTest {
        val currencyUSD = Currency.USD
        val currencyEUR = Currency.EUR
        val ratesListUSD = listOf(Rate(id = 1, exchangeId = 1, currency = "USD", buy = 1.0, sell = 0.9, createdAt = "2022-01-01T00:00:00Z"))
        val ratesListEUR = listOf(Rate(id = 2, exchangeId = 2, currency = "EUR", buy = 0.85, sell = 0.75, createdAt = "2022-01-01T00:00:00Z"))

        `when`(ratesRepository.getRatesByCurrency(currencyUSD)).thenReturn(flowOf(Resource.Success(ratesListUSD)))
        `when`(ratesRepository.getRatesByCurrency(currencyEUR)).thenReturn(flowOf(Resource.Success(ratesListEUR)))

        val resultUSD = getRatesByCurrencyUseCase.invoke(currencyUSD).first()
        assertTrue(resultUSD is Resource.Success)
        assertEquals(ratesListUSD, (resultUSD as Resource.Success).data)

        val resultEUR = getRatesByCurrencyUseCase.invoke(currencyEUR).first()
        assertTrue(resultEUR is Resource.Success)
        assertEquals(ratesListEUR, (resultEUR as Resource.Success).data)
    }

    @Test
    fun `invoke returns error when repository returns error`() = runTest {
        val currency = Currency.USD
        val errorMessage = "Error occurred"
        `when`(ratesRepository.getRatesByCurrency(currency)).thenReturn(flowOf(Resource.Error(errorMessage)))

        val result = getRatesByCurrencyUseCase.invoke(currency).first()

        assertTrue(result is Resource.Error)
        assertEquals(errorMessage, (result as Resource.Error).message)
    }

    @Test
    fun `invoke returns loading when repository returns loading`() = runTest {
        val currency = Currency.USD
        `when`(ratesRepository.getRatesByCurrency(currency)).thenReturn(flowOf(Resource.Loading()))

        val result = getRatesByCurrencyUseCase.invoke(currency).first()

        assertTrue(result is Resource.Loading)
    }

}