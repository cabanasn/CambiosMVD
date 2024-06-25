package com.ircl.cambiosmvd.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.ircl.cambiosmvd.data.local.entity.ExchangeEntity
import com.ircl.cambiosmvd.data.local.entity.RateEntity
import com.ircl.cambiosmvd.di.AppModule
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject

@HiltAndroidTest
@SmallTest
@UninstallModules(AppModule::class)
class RateDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var ratesDb: ExchangeDatabase

    private lateinit var dao: RateDao

    @Before
    fun setup() = runTest {
        hiltRule.inject()
        dao = ratesDb.rateDao

        val exchanges = listOf(
            ExchangeEntity(1, "name1", "icon1", "website1", "email1", "phone1", "address1"),
            ExchangeEntity(2, "name2", "icon2", "website2", "email2", "phone2", "address2")
        )
        ratesDb.exchangeDao.insertExchanges(exchanges)
    }

    @Test
    fun insertRates_insertsData() = runTest {
        val rates = listOf(
            RateEntity(id = 1, buy = 1.0, sell = 1.0, createdAt = "now", currency = "USD", exchangeId = 1),
            RateEntity(id = 2, buy = 0.85, sell = 0.85, createdAt = "now", currency = "EUR", exchangeId = 1)
        )

        dao.insertRates(rates)

        val result = dao.getRatesForExchange(1)
        assertEquals(rates, result)
    }

    @Test
    fun getRatesForExchange_returnsData() = runTest {
        val ratesForExchange1 = listOf(
            RateEntity(id = 1, buy = 1.0, sell = 1.0, createdAt = "now", currency = "USD", exchangeId = 1),
            RateEntity(id = 2, buy = 0.85, sell = 0.85, createdAt = "now", currency = "EUR", exchangeId = 1)
        )
        val ratesForExchange2 = listOf(
            RateEntity(id = 3, buy = 1.0, sell = 2.0, createdAt = "now", currency = "ARS", exchangeId = 2)
        )

        dao.insertRates(ratesForExchange1 + ratesForExchange2)

        val resultForExchange1 = dao.getRatesForExchange(1)
        assertEquals(ratesForExchange1, resultForExchange1)

        val resultForExchange2 = dao.getRatesForExchange(2)
        assertEquals(ratesForExchange2, resultForExchange2)

        val resultForExchange3 = dao.getRatesForExchange(3)
        assertEquals(emptyList<RateEntity>(), resultForExchange3)
    }

    @Test
    fun getRatesByCurrency_returnsData() = runTest {
        val rates = listOf(
            RateEntity(id = 1, buy = 1.0, sell = 1.0, createdAt = "now", currency = "USD", exchangeId = 1),
            RateEntity(id = 2, buy = 0.85, sell = 0.85, createdAt = "now", currency = "EUR", exchangeId = 1),
            RateEntity(id = 3, buy = 0.85, sell = 0.85, createdAt = "now", currency = "ARS", exchangeId = 1),
            RateEntity(id = 4, buy = 0.85, sell = 0.85, createdAt = "now", currency = "ARS", exchangeId = 1)
        )

        dao.insertRates(rates)

        val result1 = dao.getRatesByCurrency("USD")
        assertEquals(listOf(rates[0]), result1)

        val result2 = dao.getRatesByCurrency("EUR")
        assertEquals(listOf(rates[1]), result2)

        val result3 = dao.getRatesByCurrency("ARS")
        assertEquals(listOf(rates[2], rates[3]), result3)

        val result4 = dao.getRatesByCurrency("BRL")
        assertEquals(emptyList<RateEntity>(), result4)
    }

    @Test
    fun getRatesByCurrency_returnsEmptyListWhenNoData() = runTest {
        val result1 = dao.getRatesByCurrency("USD")
        assertEquals(emptyList<RateEntity>(), result1)

        val result2 = dao.getRatesForExchange(1)
        assertEquals(emptyList<RateEntity>(), result2)
    }

    @After
    fun teardown() {
        ratesDb.close()
    }
}