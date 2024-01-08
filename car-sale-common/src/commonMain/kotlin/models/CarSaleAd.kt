package models

data class CarSaleAd(
    var id: CarSaleAdId = CarSaleAdId.NONE,
    var carName: String = "",
    var description: String = "",
    var yearOfProduction: Int = 1977,
    var milage: Int = 0,
    var ownerId: CarSaleUserId = CarSaleUserId.NONE,
    var adStatus: CarSaleAdStatus = CarSaleAdStatus.NONE,
    var visibility: CarSaleAdVisibility = CarSaleAdVisibility.NONE,
    var carId: CarSaleCarId = CarSaleCarId.NONE,
    val permissionClient: MutableSet<CarSaleAdPermissionClient> = mutableSetOf()
){
    fun deepCopy(): CarSaleAd = copy(
        permissionClient = permissionClient.toMutableSet()
    )
}
