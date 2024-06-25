package com.ircl.cambiosmvd.domain.repository

import com.ircl.cambiosmvd.data.local.ExchangeDatabase
import com.ircl.cambiosmvd.data.mappers.asDomain
import com.ircl.cambiosmvd.data.mappers.asEntity
import com.ircl.cambiosmvd.data.remote.ExchangesAPI
import com.ircl.cambiosmvd.data.remote.response.Currency
import com.ircl.cambiosmvd.data.remote.response.Rate
import com.ircl.cambiosmvd.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RatesRepositoryImpl @Inject constructor(
    private val exchangesAPI: ExchangesAPI,
    private val exchangeDatabase: ExchangeDatabase
) : RatesRepository {

    override suspend fun getRatesByCurrency(currency: Currency): Flow<Resource<List<Rate>>> {
        return flow {
            try {
                val rates = exchangeDatabase.rateDao.getRatesByCurrency(currency.name)
                if (rates.isEmpty()) {
                    val remoteRates = fetchLatestRatesFromServer()
                    emit(Resource.Success(remoteRates.filter { it.currency == currency.name}))
                    emit(Resource.Loading(false))
                } else {
                    emit(Resource.Success(rates.asDomain()))
                    emit(Resource.Loading(false))
                }
            } catch (e: Exception) {
                // TODO - improve error handling
                emit(Resource.Error(e.localizedMessage))
                emit(Resource.Loading(false))
            }
        }
    }

    override suspend fun getRatesForExchange(exchangeId: Int): Flow<Resource<List<Rate>>> {
        return flow {
            try {
                val rates = exchangeDatabase.rateDao.getRatesForExchange(exchangeId)
                if (rates.isEmpty()) {
                    val remoteRates = fetchLatestRatesFromServer()
                    emit(Resource.Success(remoteRates.filter { it.exchangeId == exchangeId}))
                    emit(Resource.Loading(false))
                } else {
                    emit(Resource.Success(rates.asDomain()))
                    emit(Resource.Loading(false))
                }
            } catch (e: Exception) {
                // TODO - improve error handling
                emit(Resource.Error(e.localizedMessage))
                emit(Resource.Loading(false))
            }
        }
    }

    private suspend fun fetchLatestRatesFromServer(): List<Rate> {
        // Fetch Latest Rates
        val lastRateList = exchangesAPI.getLastRate()
        val lastRate = lastRateList.first()
        val creationDateFilter = "eq." +
                lastRate.createdAt.replace("+00:00", "Z")
        val remoteRates = exchangesAPI.getLatestRates(creationDateFilter)
        // Insert Rates in DB
        exchangeDatabase.rateDao.insertRates(remoteRates.asEntity())
        return remoteRates
    }

}