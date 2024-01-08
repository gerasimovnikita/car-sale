package ru.otus.otuskotlin.marketplace.biz.validation

import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import kotlin.test.assertContains
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = CarSaleAdId("123-234-abc-ABC"),
            carName = "abc",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CarSaleState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = CarSaleAdId(" \n\t 123-234-abc-ABC \n\t "),
            carName = "abc",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(CarSaleState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = CarSaleAdId(""),
            carName = "abc",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CarSaleState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: CarSaleCommand, processor: CarSaleAdProcessor) = runTest {
    val ctx = CarSaleContext(
        command = command,
        state = CarSaleState.NONE,
        workMode = CarSaleWorkMode.TEST,
        carSaleRequest = CarSaleAd(
            id = CarSaleAdId("!@#\$%^&*(),.{}"),
            carName = "abc",
            description = "abc",
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(CarSaleState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
