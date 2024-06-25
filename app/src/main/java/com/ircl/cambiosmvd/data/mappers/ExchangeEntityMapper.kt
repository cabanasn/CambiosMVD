package com.ircl.cambiosmvd.data.mappers

import com.ircl.cambiosmvd.data.local.entity.ExchangeEntity
import com.ircl.cambiosmvd.data.remote.response.Exchange

class ExchangeEntityMapper : EntityMapper<List<Exchange>, List<ExchangeEntity>> {
    override fun asEntity(domain: List<Exchange>): List<ExchangeEntity> {
        return domain.map {
            ExchangeEntity(
                id = it.id,
                icon = it.icon,
                name = it.name,
                address = it.address,
                email = it.email,
                phone = it.phone,
                website = it.website
            )
        }
    }

    override fun asDomain(entity: List<ExchangeEntity>): List<Exchange> {
        return entity.map {
            Exchange(
                id = it.id,
                icon = it.icon,
                name = it.name,
                address = it.address,
                email = it.email,
                phone = it.phone,
                website = it.website
            )
        }
    }
}

fun List<Exchange>.asEntity(): List<ExchangeEntity> {
    return ExchangeEntityMapper().asEntity(this)
}

fun List<ExchangeEntity>.asDomain(): List<Exchange> {
    return ExchangeEntityMapper().asDomain(this)
}