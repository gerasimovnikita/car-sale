package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState
import repo.DbAdRequest

fun ICorChainDsl<CarSaleContext>.repoCreate(title: String) = worker {
    this.title = title
    description = "Добавление объявления в БД"
    on { state == CarSaleState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        val result = adRepo.createAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = CarSaleState.FAILING
            errors.addAll(result.errors)
        }
    }
}
