package com.github.gerasimovnikita.otus.carsale.biz.groups

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.chain
import models.CarSaleCommand
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.operation(title: String, command: CarSaleCommand, block: ICorChainDsl<CarSaleContext>.() -> Unit) = chain {
    block()
    this.title = title
    on { this.command == command && state == CarSaleState.RUNNING }
}
