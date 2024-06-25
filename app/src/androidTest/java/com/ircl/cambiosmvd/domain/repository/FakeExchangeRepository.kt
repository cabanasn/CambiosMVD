package com.ircl.cambiosmvd.domain.repository

import com.ircl.cambiosmvd.data.remote.response.Exchange
import com.ircl.cambiosmvd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class FakeExchangeRepository : ExchangeRepository {

    override suspend fun getExchanges(): Flow<Resource<List<Exchange>>> {
        return flow {

        }
    }

}