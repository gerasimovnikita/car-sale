package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState
import repo.DbAdRequest

fun ICorChainDsl<CarSaleContext>.repoUpdate(title: String) = worker {
    this.title = title
    on { state == CarSaleState.RUNNING }
    handle {
        val request = DbAdRequest(adRepoPrepare)
        val result = adRepo.updateAd(request)
        val resultAd = result.data
        if (result.isSuccess && resultAd != null) {
            adRepoDone = resultAd
        } else {
            state = CarSaleState.FAILING
            errors.addAll(result.errors)
            adRepoDone
        }
    }
}
