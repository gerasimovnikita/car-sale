package helpers

import CarSaleContext
import models.CarSaleError

fun Throwable.asCarSaleError(
    code: String = "unknown",
    group: String = "exceptions",
    message: String = this.message ?: "",
) = CarSaleError(
    code = code,
    group = group,
    field = "",
    message = message,
    exception = this,
)

fun CarSaleContext.addError(vararg error: CarSaleError)= errors.addAll(error)