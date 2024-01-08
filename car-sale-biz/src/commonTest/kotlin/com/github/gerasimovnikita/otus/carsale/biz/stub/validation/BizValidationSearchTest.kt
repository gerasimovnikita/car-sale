package ru.otus.otuskotlin.marketplace.biz.validation

import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.CarSaleAdFilter
import models.CarSaleCommand
import models.CarSaleState
import models.CarSaleWorkMode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val command = CarSaleCommand.SEARCH
    private val processor by lazy { CarSaleAdProcessor() }

    @Test
    fun correctEmpty() = runTest {
        val ctx = CarSaleContext(
            command = command,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.TEST,
            carSaleAdFilterRequest = CarSaleAdFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(CarSaleState.FAILING, ctx.state)
    }
}

