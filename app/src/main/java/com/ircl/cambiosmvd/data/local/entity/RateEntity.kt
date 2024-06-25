package com.ircl.cambiosmvd.data.local.entity

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [ForeignKey(
        entity = ExchangeEntity::class,
        parentColumns = ["id"],
        childColumns = ["exchangeId"],
        onDelete = ForeignKey.CASCADE
    )]
)
data class RateEntity(
    val id: Int,
    val buy: Double,
    val sell: Double,
    val createdAt: String,
    val currency: String,
    val exchangeId: Int
)
