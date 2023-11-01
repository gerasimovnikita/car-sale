package exceptions

import models.CarSaleCommand

class UnknownCarSaleCommand(command: CarSaleCommand) : Throwable("Wrong command $command at mapping toTransport stage")