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
class AdUpdateStubTest {

    private val processor = CarSaleAdProcessor()
    val id = CarSaleAdId("777")
    val carName = "carName 666"
    val description = "desc 666"
    val status = CarSaleAdStatus.ACTIVE
    val visibility = CarSaleAdVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = CarSaleContext(
            command = CarSaleCommand.UPDATE,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.SUCCESS,
            carSaleRequest = CarSaleAd(
                id = id,
                carName = carName,
                description = description,
                adStatus = status,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.carSaleResponse.id)
        assertEquals(carName, ctx.carSaleResponse.carName)
        assertEquals(description, ctx.carSaleResponse.description)
        assertEquals(status, ctx.carSaleResponse.adStatus)
        assertEquals(visibility, ctx.carSaleResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.UPDATE,
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
    fun badTitle() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.UPDATE,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_CAR_NAME,
            carSaleRequest = CarSaleAd(
                id = id,
                carName = "",
                description = description,
                adStatus = status,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("carName", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.UPDATE,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_DESCRIPTION,
            carSaleRequest = CarSaleAd(
                id = id,
                carName = carName,
                description = "",
                adStatus = status,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = CarSaleContext(
            command = CarSaleCommand.UPDATE,
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
            command = CarSaleCommand.UPDATE,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.STUB,
            stubCase = CarSaleStubs.BAD_SEARCH_STRING,
            carSaleRequest = CarSaleAd(
                id = id,
                carName = carName,
                description = description,
                adStatus = status,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(CarSaleAd(), ctx.carSaleResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
