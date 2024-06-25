package com.ircl.cambiosmvd.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.ircl.cambiosmvd.data.local.entity.ExchangeEntity

@Dao
interface ExchangeDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertExchanges(exchanges: List<ExchangeEntity>)

    @Query("SELECT * FROM ExchangeEntity")
    suspend fun getExchanges(): List<ExchangeEntity>
}