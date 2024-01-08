package com.github.gerasimovnikita.otus.carsale.biz.validation

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.errorValidation
import helpers.fail

fun ICorChainDsl<CarSaleContext>.validateDescriptionHasContent(title: String) = worker {
    this.title = title
    val regExp = Regex("\\p{L}")
    on { carSaleAdValidating.description.isNotEmpty() && !carSaleAdValidating.description.contains(regExp) }
    handle {
        fail(
            errorValidation(
                field = "description",
                violationCode = "noContent",
                description = "field must contain letters"
            )
        )
    }
}
