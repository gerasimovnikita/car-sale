package helpers

import CarSaleContext
import models.CarSaleError
import models.CarSaleState

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

fun CarSaleContext.fail(error: CarSaleError){
    addError(error)
    state = CarSaleState.FAILING
}