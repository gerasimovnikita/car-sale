package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState
import models.CarSaleWorkMode

fun ICorChainDsl<CarSaleContext>.prepareResult(title: String) = worker {
    this.title = title
    description = "Подготовка данных для ответа клиенту на запрос"
    on { workMode != CarSaleWorkMode.STUB }
    handle {
        carSaleResponse = adRepoDone
        carSaleAdsResponse = adsRepoDone
        state = when (val st = state) {
            CarSaleState.RUNNING -> CarSaleState.FINISHING
            else -> st
        }
    }
}
