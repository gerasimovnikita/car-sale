package com.github.gerasimovnikita.otus.carsale.biz

import CarSaleAdStub
import CarSaleContext
import models.CarSaleAdStatus
import models.CarSaleCommand
import models.CarSaleWorkMode

class CarSaleAdProcessor {
    suspend fun exec(ctx: CarSaleContext) {
        // TODO: Rewrite temporary stub solution with BIZ
        require(ctx.workMode == CarSaleWorkMode.STUB) {
            "Currently working only in STUB mode."
        }

        when (ctx.command) {
            CarSaleCommand.SEARCH -> {
                ctx.carSaleAdsResponse.addAll(CarSaleAdStub.prepareSearchList("Болт", CarSaleAdStatus.ACTIVE))
            }
            else -> {
                ctx.carSaleResponse = CarSaleAdStub.get()
            }
        }
    }
}
