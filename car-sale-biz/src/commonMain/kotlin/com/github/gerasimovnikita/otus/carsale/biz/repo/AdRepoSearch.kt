package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState
import repo.DbAdFilterRequest

fun ICorChainDsl<CarSaleContext>.repoSearch(title: String) = worker {
    this.title = title
    description = "Поиск объявлений в БД по фильтру"
    on { state == CarSaleState.RUNNING }
    handle {
        val request = DbAdFilterRequest(
            titleFilter = carSaleAdFilterValidated.searchString,
            ownerId = carSaleAdFilterValidated.ownerId
        )
        val result = adRepo.searchAd(request)
        val resultAds = result.data
        if (result.isSuccess && resultAds != null) {
            adsRepoDone = resultAds.toMutableList()
        } else {
            state = CarSaleState.FAILING
            errors.addAll(result.errors)
        }
    }
}
