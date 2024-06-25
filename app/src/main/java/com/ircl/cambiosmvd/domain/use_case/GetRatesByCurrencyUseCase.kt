package com.ircl.cambiosmvd.domain.use_case

import com.ircl.cambiosmvd.data.remote.response.Currency
import com.ircl.cambiosmvd.domain.repository.RatesRepository
import javax.inject.Inject

class GetRatesByCurrencyUseCase @Inject constructor(
    private val ratesRepository: RatesRepository
){
    suspend operator fun invoke(currency: Currency) = ratesRepository.getRatesByCurrency(currency)
}