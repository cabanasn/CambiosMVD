package com.ircl.cambiosmvd.di

import android.app.Application
import androidx.room.Room
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.ircl.cambiosmvd.data.local.ExchangeDatabase
import com.ircl.cambiosmvd.data.remote.ExchangesAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    private val loggingInterceptor: HttpLoggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val headersInterceptor = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("apikey", ExchangesAPI.API_KEY)
            .addHeader("Authorization", "Bearer ${ExchangesAPI.API_KEY}")
            .build()
        chain.proceed(request)
    }

    private val client: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .addInterceptor(headersInterceptor)
        .build()

    @Singleton
    @Provides
    fun providesExchangeAPI(): ExchangesAPI {
        return Retrofit.Builder()
            .baseUrl(ExchangesAPI.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ExchangesAPI::class.java)
    }

    @Singleton
    @Provides
    fun providesGson(): Gson {
        return GsonBuilder().create()
    }

    @Singleton
    @Provides
    fun providesExchangeDatabase(app: Application, gson: Gson): ExchangeDatabase {
        return Room.databaseBuilder(app, ExchangeDatabase::class.java,
            "exchanges_database")
            .fallbackToDestructiveMigration()
            .build()
    }


}