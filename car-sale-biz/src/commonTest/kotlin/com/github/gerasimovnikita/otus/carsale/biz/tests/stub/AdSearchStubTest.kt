package com.github.gerasimovnikita.otus.carsale.biz.tests.stub

import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import stubs.CarSaleStubs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class AdSearchStubTest {

    private val processor = CarSaleAdProcessor()
    val filter = CarSaleAdFilter(searchString = "bolt")

    @Test
    fun read() = runTest {

        val ctx = CarSaleContext(
            command = CarSaleCommand.SEARCH,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.SUCCESS,
            carSaleAdFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.carSaleAdsResponse.size > 1)
        val first = ctx.carSaleAdsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.carName.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        with (CarSaleAdStub.get()) {
            assertEquals(adStatus, first.adStatus)
            assertEquals(visibility, first.visibility)
        }
    }

    @Test
    fun badId() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.SEARCH,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_ID,
            carSaleAdFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.SEARCH,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.DB_ERROR,
            carSaleAdFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.SEARCH,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_CAR_NAME,
            carSaleAdFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
