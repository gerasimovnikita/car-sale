package com.github.gerasimovnikita.otus.carsale.biz.tests.validation

import CarSaleCorSettings
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gerasimovnikita.otus.carsale.repository.inmemory.AdRepoStub
import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.CarSaleCommand
import kotlin.test.Test

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val command = CarSaleCommand.CREATE
    private val processor  = CarSaleAdProcessor(CarSaleCorSettings(repoTest = AdRepoStub()))

    @Test fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

}

