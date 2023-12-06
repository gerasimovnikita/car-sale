package com.github.gerasimovnikita.otus.carsale.biz.groups

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.chain
import models.CarSaleState
import models.CarSaleWorkMode

fun ICorChainDsl<CarSaleContext>.stubs(title: String, block: ICorChainDsl<CarSaleContext>.() -> Unit) = chain {
    block()
    this.title = title
    on {workMode == CarSaleWorkMode.STUB && state == CarSaleState.RUNNING}
}