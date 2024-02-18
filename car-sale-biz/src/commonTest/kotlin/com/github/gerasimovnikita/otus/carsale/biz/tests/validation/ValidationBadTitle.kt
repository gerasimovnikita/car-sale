package com.github.gerasimovnikita.otus.carsale.biz.tests.validation

import CarSaleAdStub
import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

private val stub = CarSaleAdStub.get()

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleCorrect(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = stub.id,
            carName = "abc",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CarSaleState.FAILING, ctx.state)
    assertEquals("abc", ctx.carSaleAdValidated.carName)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleTrim(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = stub.id,
            carName = " \n\t abc \t\n ",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CarSaleState.FAILING, ctx.state)
    assertEquals("abc", ctx.carSaleAdValidated.carName)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = stub.id,
            carName = "",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CarSaleState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = CarSaleAdId("123"),
            carName = "!@#\$%^&*(),.{}",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CarSaleState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
