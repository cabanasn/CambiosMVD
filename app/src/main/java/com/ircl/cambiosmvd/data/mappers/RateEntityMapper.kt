package com.ircl.cambiosmvd.data.mappers

import com.ircl.cambiosmvd.data.local.entity.RateEntity
import com.ircl.cambiosmvd.data.remote.response.Rate

class RateEntityMapper : EntityMapper<List<Rate>, List<RateEntity>> {
    override fun asEntity(domain: List<Rate>): List<RateEntity> {
        return domain.map {
            RateEntity(
                buy = it.buy,
                createdAt = it.createdAt,
                currency = it.currency,
                exchangeId = it.exchangeId,
                id = it.id,
                sell = it.sell
            )
        }
    }

    override fun asDomain(entity: List<RateEntity>): List<Rate> {
        return entity.map {
            Rate(
                buy = it.buy,
                createdAt = it.createdAt,
                currency = it.currency,
                exchangeId = it.exchangeId,
                id = it.id,
                sell = it.sell
            )
        }
    }
}

fun List<Rate>.asEntity(): List<RateEntity> {
    return RateEntityMapper().asEntity(this)
}

fun List<RateEntity>.asDomain(): List<Rate> {
    return RateEntityMapper().asDomain(this)
}