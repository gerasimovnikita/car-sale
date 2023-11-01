package models

data class CarSaleAdFilter(
    var searchString: String = "",
    var ownerId: CarSaleUserId = CarSaleUserId.NONE,
    var status: CarSaleAdStatus = CarSaleAdStatus.NONE
)
