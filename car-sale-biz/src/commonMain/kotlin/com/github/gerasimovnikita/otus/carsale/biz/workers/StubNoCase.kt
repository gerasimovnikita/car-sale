package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.fail
import models.CarSaleError
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.stubNoCase(title: String) = worker {
    this.title = title
    on { state == CarSaleState.RUNNING }
    handle {
        fail(
            CarSaleError(
                code = "validation",
                field = "stub",
                group = "validation",
                message = "Wrong stub case is requested: ${stubCase.name}"
            )
        )
    }
}
