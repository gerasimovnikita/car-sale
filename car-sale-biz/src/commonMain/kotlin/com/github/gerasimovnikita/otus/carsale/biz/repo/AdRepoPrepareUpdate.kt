package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleState

fun ICorChainDsl<CarSaleContext>.repoPrepareUpdate(title: String) = worker {
    this.title = title
    description = "Готовим данные к сохранению в БД: совмещаем данные, прочитанные из БД, " +
            "и данные, полученные от пользователя"
    on { state == CarSaleState.RUNNING }
    handle {
        adRepoPrepare = adRepoRead.deepCopy().apply {
            this.carName = carSaleAdValidated.carName
            description = carSaleAdValidated.description
            adStatus = carSaleAdValidated.adStatus
            visibility = carSaleAdValidated.visibility
        }
    }
}
