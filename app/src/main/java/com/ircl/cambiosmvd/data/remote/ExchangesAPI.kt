package com.ircl.cambiosmvd.data.remote

import com.ircl.cambiosmvd.data.remote.response.Exchange
import com.ircl.cambiosmvd.data.remote.response.Rate
import retrofit2.http.GET
import retrofit2.http.Query

interface ExchangesAPI {

    @GET("exchange?select=*")
    suspend fun getExchanges(): List<Exchange>

    @GET("rate?order=id.desc&limit=1")
    suspend fun getLastRate(): List<Rate>

    @GET("rate")
    suspend fun getLatestRates(
        @Query("created_at") createdAt: String
    ): List<Rate>

    companion object {
        const val BASE_URL = "https://chaqswjuxomcypsojrhv.supabase.co/rest/v1/"
        const val API_KEY = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6ImNoYXFzd2p1eG9tY3lwc29qcmh2Iiwicm9sZSI6ImFub24iLCJpYXQiOjE3MTg2NTA4MTIsImV4cCI6MjAzNDIyNjgxMn0.MvvRdngufTX0MNYNOdca8Oi_Lcdhrm5yHO51zWdygG4"
    }

}