package com.ircl.cambiosmvd.domain.use_case

import com.ircl.cambiosmvd.domain.repository.ExchangeRepository
import javax.inject.Inject

class GetExchangesUseCase @Inject constructor(
    private val exchangeRepository: ExchangeRepository
) {
    suspend operator fun invoke() = exchangeRepository.getExchanges()
}