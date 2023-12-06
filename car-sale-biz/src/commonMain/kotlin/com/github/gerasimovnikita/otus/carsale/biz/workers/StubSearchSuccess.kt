package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleAdStub
import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleAdStatus
import models.CarSaleState
import stubs.CarSaleStubs

fun ICorChainDsl<CarSaleContext>.stubSearchSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CarSaleStubs.SUCCESS && state == CarSaleState.RUNNING }
    handle {
        state = CarSaleState.FINISHING
        carSaleAdsResponse.addAll(CarSaleAdStub.prepareSearchList(carSaleAdFilterRequest.searchString, CarSaleAdStatus.ACTIVE))
    }
}
