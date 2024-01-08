package com.github.gerasimovnikita.otus.carsale.biz.stub.stub

import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import stubs.CarSaleStubs
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AdDeleteStubTest {

    private val processor = CarSaleAdProcessor()
    val id = CarSaleAdId("666")

    @Test
    fun delete() = runTest {

        val ctx = CarSaleContext(
            command = CarSaleCommand.DELETE,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.SUCCESS,
            carSaleRequest = CarSaleAd(
                id = id,
            ),
        )
        processor.exec(ctx)

        val stub = CarSaleAdStub.get()
        assertEquals(stub.id, ctx.carSaleResponse.id)
        assertEquals(stub.carName, ctx.carSaleResponse.carName)
        assertEquals(stub.description, ctx.carSaleResponse.description)
        assertEquals(stub.visibility, ctx.carSaleResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.DELETE,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_ID,
            carSaleRequest = CarSaleAd(),
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.DELETE,
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
            command = CarSaleCommand.DELETE,
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
