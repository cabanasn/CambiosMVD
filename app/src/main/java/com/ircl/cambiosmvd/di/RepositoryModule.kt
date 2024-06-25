package com.ircl.cambiosmvd.di

import com.ircl.cambiosmvd.domain.repository.ExchangeRepository
import com.ircl.cambiosmvd.domain.repository.ExchangeRepositoryImpl
import com.ircl.cambiosmvd.domain.repository.RatesRepository
import com.ircl.cambiosmvd.domain.repository.RatesRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindExchangeRepository(exchangeRepositoryImpl: ExchangeRepositoryImpl): ExchangeRepository

    @Binds
    @Singleton
    abstract fun bindRatesRepository(ratesRepositoryImpl: RatesRepositoryImpl): RatesRepository

}