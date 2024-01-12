package com.github.gerasimovnikita.otus.carsale.biz.tests.repo

import CarSaleContext
import CarSaleCorSettings
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gerasimovnikita.otus.carsale.repo.tests.AdRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import repo.DbAdResponse
import kotlin.test.assertEquals

private val initAd = CarSaleAd(
    id = CarSaleAdId("123"),
    carName = "abc",
    description = "abc",
    adStatus = CarSaleAdStatus.ACTIVE,
    visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
)
private val repo = AdRepositoryMock(
        invokeReadAd = {
            if (it.id == initAd.id) {
                DbAdResponse(
                    isSuccess = true,
                    data = initAd,
                )
            } else DbAdResponse(
                isSuccess = false,
                data = null,
                errors = listOf(CarSaleError(message = "Not found", field = "id"))
            )
        }
    )
private val settings by lazy {
    CarSaleCorSettings(
        repoTest = repo
    )
}
private val processor by lazy { CarSaleAdProcessor(settings) }

@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(command: CarSaleCommand) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = CarSaleAdId("12345"),
            carName = "xyz",
            description = "xyz",
            adStatus = CarSaleAdStatus.ACTIVE,
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC"),

        ),
    )
    processor.exec(ctx)
    assertEquals(CarSaleState.FAILING, ctx.state)
    assertEquals(CarSaleAd(), ctx.carSaleResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
