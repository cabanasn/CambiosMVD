package com.ircl.cambiosmvd.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ircl.cambiosmvd.data.local.entity.RateEntity

@Dao
interface RateDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRates(exchanges: List<RateEntity>)

    @Query("SELECT * FROM RateEntity WHERE id = :exchangeId")
    suspend fun getRatesForExchange(exchangeId: Int): List<RateEntity>

    @Query("SELECT * FROM RateEntity WHERE currency = :currency")
    suspend fun getRatesByCurrency(currency: String): List<RateEntity>
}
