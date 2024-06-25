package com.ircl.cambiosmvd.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.ircl.cambiosmvd.data.local.entity.ExchangeEntity
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
class ExchangeDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    lateinit var exchangesDb: ExchangeDatabase

    private lateinit var dao: ExchangeDao

    @Before
    fun setup() {
        hiltRule.inject()
        dao = exchangesDb.exchangeDao
    }

    @Test
    fun getExchanges_returnsEmptyListWhenNoData() = runTest {
        val result = dao.getExchanges()
        assertEquals(emptyList<ExchangeEntity>(), result)
    }

    @Test
    fun insertSingleExchange_insertsData() = runTest {
        val exchange = ExchangeEntity(1, "name1", "icon1", "website1", "email1", "phone1", "address1")

        dao.insertExchanges(listOf(exchange))

        val result = dao.getExchanges()
        assertEquals(listOf(exchange), result)
    }

    @Test
    fun insertMultipleExchanges_insertsData() = runTest {
        val exchanges = listOf(
            ExchangeEntity(1, "name1", "icon1", "website1", "email1", "phone1", "address1"),
            ExchangeEntity(2, "name2", "icon2", "website2", "email2", "phone2", "address2")
        )

        dao.insertExchanges(exchanges)

        val result = dao.getExchanges()
        assertEquals(exchanges, result)
    }

    @Test
    fun insertSameExchangeTwice_updatesData() = runTest {
        val initialExchange = ExchangeEntity(1, "name1", "icon1", "website1", "email1", "phone1", "address1")
        val updatedExchange = ExchangeEntity(1, "name2", "icon2", "website2", "email2", "phone2", "address2")

        // Insert the initial exchange
        dao.insertExchanges(listOf(initialExchange))

        // Retrieve the exchange and check if it's the initial one
        val result1 = dao.getExchanges()
        assertEquals(listOf(initialExchange), result1)

        // Insert the updated exchange with the same id
        dao.insertExchanges(listOf(updatedExchange))

        // Retrieve the exchange and check if it's the updated one
        val result2 = dao.getExchanges()
        assertEquals(listOf(updatedExchange), result2)
    }

    @After
    fun teardown() {
        exchangesDb.close()
    }

}