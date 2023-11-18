package helpers

import CarSaleContext
import models.CarSaleCommand

fun CarSaleContext.isUpdatableCommand () =
    this.command in listOf(CarSaleCommand.UPDATE, CarSaleCommand.CREATE, CarSaleCommand.DELETE)