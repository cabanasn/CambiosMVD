package com.ircl.cambiosmvd.di

import com.ircl.cambiosmvd.domain.repository.ExchangeRepository
import com.ircl.cambiosmvd.domain.repository.FakeExchangeRepository
import com.ircl.cambiosmvd.domain.repository.FakeRatesRepository
import com.ircl.cambiosmvd.domain.repository.RatesRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class TestRepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExchangeRepository(exchangeRepositoryImpl: FakeExchangeRepository): ExchangeRepository

    @Binds
    @Singleton
    abstract fun bindRatesRepository(ratesRepositoryImpl: FakeRatesRepository): RatesRepository

}