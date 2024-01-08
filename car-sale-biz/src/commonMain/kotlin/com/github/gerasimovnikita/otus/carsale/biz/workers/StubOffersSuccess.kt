package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleAdStub
import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleAdId
import models.CarSaleAdStatus
import models.CarSaleState
import stubs.CarSaleStubs

fun ICorChainDsl<CarSaleContext>.stubOffersSuccess(title: String) = worker {
    this.title = title
    on { stubCase == CarSaleStubs.SUCCESS && state == CarSaleState.RUNNING }
    handle {
        state = CarSaleState.FINISHING
        carSaleResponse = CarSaleAdStub.prepareResult {
            carSaleRequest.id.takeIf { it != CarSaleAdId.NONE }?.also { this.id = it }
        }
        carSaleAdsResponse.addAll(CarSaleAdStub.prepareOffersList(carSaleResponse.carName, CarSaleAdStatus.ACTIVE))
    }
}
