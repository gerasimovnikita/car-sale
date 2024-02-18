package repo

import models.CarSaleState
import models.CarSaleUserId

data class DbAdFilterRequest(
    val titleFilter: String = "",
    val ownerId: CarSaleUserId = CarSaleUserId.NONE,
    val state: CarSaleState = CarSaleState.NONE,
)
