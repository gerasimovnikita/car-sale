package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.repoPrepareCreate(title: String) = worker {
    this.title = title
    description = "Подготовка объекта к сохранению в базе данных"
    on { state == CarSaleState.RUNNING }
    handle {
        adRepoRead = carSaleAdValidated.deepCopy()
        adRepoPrepare = adRepoRead

    }
}
