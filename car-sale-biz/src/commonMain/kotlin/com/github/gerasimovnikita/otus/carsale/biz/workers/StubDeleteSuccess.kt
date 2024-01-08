package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleAdStub
import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState
import stubs.CarSaleStubs

fun ICorChainDsl<CarSaleContext>.stubDeleteSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CarSaleStubs.SUCCESS && state == CarSaleState.RUNNING }
    handle {
        state = CarSaleState.FINISHING
        carSaleResponse = CarSaleAdStub.prepareResult {
            carSaleRequest.carName.takeIf { it.isNotBlank() }?.also { this.carName = it }
        }
    }
}
