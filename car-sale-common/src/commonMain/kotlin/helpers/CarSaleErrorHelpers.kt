package helpers

import CarSaleContext
import exceptions.RepoConcurrencyException
import models.CarSaleAdLock
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

fun errorValidation(
    field: String,
    violationCode: String,
    description: String,
    level: CarSaleError.Level = CarSaleError.Level.ERROR,
) = CarSaleError(
    code = "validation-$field-$violationCode",
    field = field,
    group = "validation",
    message = "Validation error for field $field: $description",
    level = level,
)

fun errorRepoConcurrency(
    expectedLock: CarSaleAdLock,
    actualLock: CarSaleAdLock?,
    exception: Exception? = null,
) = CarSaleError(
    field = "lock",
    code = "concurrency",
    group = "repo",
    message = "The object has been changed concurrently by another user or process",
    exception = exception ?: RepoConcurrencyException(expectedLock, actualLock),
)