package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.initStatus(title: String) = worker() {
    this.title = title
    on { state == CarSaleState.NONE }
    handle { state = CarSaleState.RUNNING }
}