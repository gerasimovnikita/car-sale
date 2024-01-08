package com.github.gerasimovnikita.otus.carsale.biz.validation

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.finishAdValidation(title: String) = worker {
    this.title = title
    on { state == CarSaleState.RUNNING }
    handle {
        carSaleAdValidated = carSaleAdValidating
    }
}

fun ICorChainDsl<CarSaleContext>.finishAdFilterValidation(title: String) = worker {
    this.title = title
    on { state == CarSaleState.RUNNING }
    handle {
        carSaleAdFilterValidated = carSaleAdFilterValidating
    }
}
