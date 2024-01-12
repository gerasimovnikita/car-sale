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
class AdOffersStubTest {

    private val processor = CarSaleAdProcessor()
    val id = CarSaleAdId("777")

    @Test
    fun offers() = runTest {

        val ctx = CarSaleContext(
            command = CarSaleCommand.OFFERS,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.SUCCESS,
            carSaleRequest = CarSaleAd(
                id = id,
            ),
        )
        processor.exec(ctx)

        assertEquals(id, ctx.carSaleResponse.id)

        with(CarSaleAdStub.get()) {
            assertEquals(carName, ctx.carSaleResponse.carName)
            assertEquals(description, ctx.carSaleResponse.description)
            assertEquals(visibility, ctx.carSaleResponse.visibility)
        }

        assertTrue(ctx.carSaleAdsResponse.size > 1)
        val first = ctx.carSaleAdsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.carName.contains(ctx.carSaleResponse.carName))
        assertTrue(first.description.contains(ctx.carSaleResponse.carName))
        assertEquals(CarSaleAdStub.get().visibility, first.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.OFFERS,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_ID,
            carSaleRequest = CarSaleAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.OFFERS,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.DB_ERROR,
            carSaleRequest = CarSaleAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.OFFERS,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_CAR_NAME,
            carSaleRequest = CarSaleAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
