package com.ircl.cambiosmvd.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class ExchangeEntity(
    @PrimaryKey
    val id: Int,
    val icon: String,
    val name: String,
    val address: String? = null,
    val email: String? = null,
    val phone: String? = null,
    val website: String? = null
)