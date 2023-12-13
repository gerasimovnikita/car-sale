package com.github.gerasimovnikita.otus.carsale.biz.validation

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.chain
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.validation(block: ICorChainDsl<CarSaleContext>.() -> Unit) = chain {
    block()
    title = "Валидация"

    on { state == CarSaleState.RUNNING }
}
