package com.ircl.cambiosmvd.domain.use_case

import com.ircl.cambiosmvd.domain.repository.RatesRepository
import javax.inject.Inject

class GetRatesForExchangeUseCase @Inject constructor(
    private val ratesRepository: RatesRepository
){
    suspend operator fun invoke(exchangeId: Int) = ratesRepository.getRatesForExchange(exchangeId)
}