package com.ircl.cambiosmvd.domain.repository

import com.ircl.cambiosmvd.data.remote.response.Currency
import com.ircl.cambiosmvd.data.remote.response.Rate
import com.ircl.cambiosmvd.utils.Resource
import kotlinx.coroutines.flow.Flow

interface RatesRepository {
    suspend fun getRatesByCurrency(currency: Currency): Flow<Resource<List<Rate>>>
    suspend fun getRatesForExchange(exchangeId: Int): Flow<Resource<List<Rate>>>
}