package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.fail
import models.CarSaleError
import models.CarSaleState
import stubs.CarSaleStubs

fun ICorChainDsl<CarSaleContext>.stubValidationBadCarName(title: String) = worker {
    this.title = title
    on {stubCase == CarSaleStubs.BAD_CAR_NAME && state == CarSaleState.RUNNING }
    handle {
        fail(
            CarSaleError(
                group = "validation",
                code = "validation-car-name",
                field = "carName",
                message = "Wrong car name"
            )
        )
    }
}