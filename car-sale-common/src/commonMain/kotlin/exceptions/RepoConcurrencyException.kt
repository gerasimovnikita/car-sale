package exceptions

import models.CarSaleAdLock

class RepoConcurrencyException(expectedLock: CarSaleAdLock, actualLock: CarSaleAdLock?): RuntimeException(
    "Expected lock is $expectedLock while actual lock in db is $actualLock"
)
