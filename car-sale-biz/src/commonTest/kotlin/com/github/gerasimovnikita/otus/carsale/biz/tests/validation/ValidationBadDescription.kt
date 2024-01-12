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
fun validationDescriptionCorrect(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
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
    assertEquals("abc", ctx.carSaleAdValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = stub.id,
            carName = "abc",
            description = " \n\tabc \n\t",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CarSaleState.FAILING, ctx.state)
    assertEquals("abc", ctx.carSaleAdValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = stub.id,
            carName = "abc",
            description = "",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CarSaleState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = stub.id,
            carName = "abc",
            description = "!@#$%^&*(),.{}",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC")
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CarSaleState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
