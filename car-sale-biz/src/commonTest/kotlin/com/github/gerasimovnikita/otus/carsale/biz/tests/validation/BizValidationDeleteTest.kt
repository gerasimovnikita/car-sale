package com.github.gerasimovnikita.otus.carsale.biz.tests.validation

import CarSaleCorSettings
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gerasimovnikita.otus.carsale.repository.inmemory.AdRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.CarSaleCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationDeleteTest {

    private val command = CarSaleCommand.DELETE
    private val processor  = CarSaleAdProcessor(CarSaleCorSettings(repoTest = AdRepoStub()))

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)


}

