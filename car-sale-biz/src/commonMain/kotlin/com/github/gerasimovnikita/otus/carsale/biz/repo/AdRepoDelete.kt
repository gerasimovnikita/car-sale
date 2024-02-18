package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState
import repo.DbAdIdRequest

fun ICorChainDsl<CarSaleContext>.repoDelete(title: String) = worker {
    this.title = title
    description = "Удаление объявления из БД по ID"
    on { state == CarSaleState.RUNNING }
    handle {
        val request = DbAdIdRequest(adRepoPrepare)
        val result = adRepo.deleteAd(request)
        if (!result.isSuccess) {
            state = CarSaleState.FAILING
            errors.addAll(result.errors)
        }
        adRepoDone = adRepoRead
    }
}
