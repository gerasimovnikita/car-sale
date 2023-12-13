package com.github.gerasimovnikita.otus.carsale.biz.validation

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.errorValidation
import helpers.fail
import models.CarSaleAdId

fun ICorChainDsl<CarSaleContext>.validateIdProperFormat(title: String) = worker {
    this.title = title
    val regExp = Regex("^[0-9a-zA-Z-]+$")
    on { carSaleAdValidating.id != CarSaleAdId.NONE && !carSaleAdValidating.id.asString().matches(regExp) }
    handle {
        val encodedId = carSaleAdValidating.id.asString()
            .replace("<", "&lt;")
            .replace(">", "&gt;")
        fail(
            errorValidation(
                field = "id",
                violationCode = "badFormat",
                description = "value $encodedId must contain only letters and numbers"
            )
        )
    }
}
