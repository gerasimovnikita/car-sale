package com.github.gerasimovnikita.otus.carsale.biz.validation

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.errorValidation
import helpers.fail

// TODO-validation-4: смотрим пример COR DSL валидации
fun ICorChainDsl<CarSaleContext>.validateTitleNotEmpty(title: String) = worker {
    this.title = title
    on { carSaleAdValidating.carName.isEmpty() }
    handle {
        fail(
            errorValidation(
                field = "title",
                violationCode = "empty",
                description = "field must not be empty"
            )
        )
    }
}
