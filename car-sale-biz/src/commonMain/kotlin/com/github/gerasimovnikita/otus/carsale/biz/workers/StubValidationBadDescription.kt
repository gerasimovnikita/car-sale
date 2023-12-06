package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.fail
import models.CarSaleError
import models.CarSaleState
import stubs.CarSaleStubs

fun ICorChainDsl<CarSaleContext>.stubValidationBadDescription(title: String) = worker {
    this.title = title
    on { stubCase == CarSaleStubs.BAD_DESCRIPTION && state == CarSaleState.RUNNING }
    handle {
        fail(
            CarSaleError(
                group = "validation",
                code = "validation-description",
                field = "description",
                message = "Wrong description"
            )
        )
    }
}