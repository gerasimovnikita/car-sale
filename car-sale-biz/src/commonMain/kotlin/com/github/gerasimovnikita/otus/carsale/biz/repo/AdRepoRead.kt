package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState
import repo.DbAdIdRequest

fun ICorChainDsl<CarSaleContext>.repoRead(title: String) = worker {
    this.title = title
    description = "Чтение объявления из БД"
    on { state == CarSaleState.RUNNING }
    handle {
        val request = DbAdIdRequest(carSaleAdValidated)
        val result = adRepo.readAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoRead = resultAd
        } else {
            state = CarSaleState.FAILING
            errors.addAll(result.errors)
        }
    }
}
