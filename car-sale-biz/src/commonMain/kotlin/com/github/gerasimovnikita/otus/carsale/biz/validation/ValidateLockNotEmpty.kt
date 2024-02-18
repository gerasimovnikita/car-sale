package com.github.gerasimovnikita.otus.carsale.biz.validation

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.errorValidation
import helpers.fail


fun ICorChainDsl<CarSaleContext>.validateLockNotEmpty(title: String) = worker {
    this.title = title
    on { carSaleAdValidating.lock.asString().isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "lock",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
