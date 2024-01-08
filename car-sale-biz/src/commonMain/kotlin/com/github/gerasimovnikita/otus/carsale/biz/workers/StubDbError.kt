package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.fail
import models.CarSaleError
import models.CarSaleState
import stubs.CarSaleStubs

fun ICorChainDsl<CarSaleContext>.stubDbError(title: String) = worker {
    this.title = title
    on { stubCase == CarSaleStubs.DB_ERROR && state == CarSaleState.RUNNING }
    handle {
        state = CarSaleState.FAILING
        fail(
            CarSaleError(
                group = "internal",
                code = "internal-db",
                message = "Internal error"
            )
        )
    }
}