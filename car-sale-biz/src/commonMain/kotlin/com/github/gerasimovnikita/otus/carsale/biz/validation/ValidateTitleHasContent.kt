package com.github.gerasimovnikita.otus.carsale.biz.validation

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.errorValidation
import helpers.fail

fun ICorChainDsl<CarSaleContext>.validateTitleHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { carSaleAdValidating.carName.isNotEmpty() && !carSaleAdValidating.carName.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "noContent",
                description = "field must contain leters"
            )
        )
    }
}
