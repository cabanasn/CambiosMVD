package com.ircl.cambiosmvd.di

import com.ircl.cambiosmvd.domain.repository.ExchangeRepository
import com.ircl.cambiosmvd.domain.use_case.GetExchangesUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideGetExchangesUseCase(
        exchangeRepository: ExchangeRepository
    ): GetExchangesUseCase {
        return GetExchangesUseCase(exchangeRepository)
    }

}