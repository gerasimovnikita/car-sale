package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.repoPrepareDelete(title: String) = worker {
    this.title = title
    description = """
        Готовим данные к удалению из БД
    """.trimIndent()
    on { state == CarSaleState.RUNNING }
    handle {
        adRepoPrepare = carSaleAdValidated.deepCopy()
    }
}
