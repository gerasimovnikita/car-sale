package com.github.gerasimovnikita.otus.carsale.biz.workers

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleAdStatus
import models.CarSaleAdVisibility
import models.CarSaleState
import stubs.CarSaleStubs
import java.time.LocalDate

fun ICorChainDsl<CarSaleContext>.stubCreateSuccess(title: String) = worker {
    this.title = title
    on {stubCase == CarSaleStubs.SUCCESS && state == CarSaleState.RUNNING }
    handle {

        val stub = CarSaleAdStub.prepareResult {
            carSaleRequest.carName.takeIf { it.isNotBlank() }?.also{this.carName = it}
            carSaleRequest.description.takeIf { it.isNotBlank() }?.also { this.description = it }
            carSaleRequest.adStatus.takeIf { it != CarSaleAdStatus.NONE }?.also { this.adStatus = it }
            carSaleRequest.milage.takeIf { it != 0 }?.also { this.milage = it }
            carSaleRequest.yearOfProduction.takeIf { it > 1900 && it <= LocalDate.now().year }?.also {this.yearOfProduction = it}
            carSaleRequest.visibility.takeIf { it != CarSaleAdVisibility.NONE }?.also { this.visibility = it }
        }
        carSaleResponse = stub
    }
}