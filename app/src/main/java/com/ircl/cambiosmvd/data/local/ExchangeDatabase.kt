package com.ircl.cambiosmvd.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ircl.cambiosmvd.data.local.entity.ExchangeEntity
import com.ircl.cambiosmvd.data.local.entity.RateEntity

@Database(
    entities = [ExchangeEntity::class, RateEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ExchangeDatabase : RoomDatabase() {
    abstract val exchangeDao: ExchangeDao
    abstract val rateDao: RateDao
}