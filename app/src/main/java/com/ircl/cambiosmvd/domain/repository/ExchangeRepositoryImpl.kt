package com.ircl.cambiosmvd.domain.repository

import com.ircl.cambiosmvd.data.local.ExchangeDatabase
import com.ircl.cambiosmvd.data.mappers.asDomain
import com.ircl.cambiosmvd.data.mappers.asEntity
import com.ircl.cambiosmvd.data.remote.ExchangesAPI
import com.ircl.cambiosmvd.data.remote.response.Exchange
import com.ircl.cambiosmvd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExchangeRepositoryImpl @Inject constructor(
    private val exchangesAPI: ExchangesAPI,
    private val exchangeDatabase: ExchangeDatabase
) : ExchangeRepository {

    override suspend fun getExchanges(): Flow<Resource<List<Exchange>>> {
        return flow {
            try {
                emit(Resource.Loading())
                val exchanges = exchangeDatabase.exchangeDao.getExchanges()
                if (exchanges.isEmpty()) {
                    val remoteExchanges = exchangesAPI.getExchanges()
                    exchangeDatabase.exchangeDao.insertExchanges(remoteExchanges.asEntity())
                    emit(Resource.Success(remoteExchanges))
                    emit(Resource.Loading(false))
                } else {
                    emit(Resource.Success(exchanges.asDomain()))
                    emit(Resource.Loading(false))
                }
            } catch (e: Exception) {
                // TODO - improve error handling
                emit(Resource.Error(e.localizedMessage))
                emit(Resource.Loading(false))
            }
        }
    }

}