import models.*

object CarSaleAdStubBolts {
    val AD_ACTIVE_BOLT1: CarSaleAd
        get() = CarSaleAd(
            id= CarSaleAdId("111"),
            carName = "car",
            description = "some car",
            milage = 99999,
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            carId = CarSaleCarId("1"),
            adStatus = CarSaleAdStatus.ACTIVE,
            permissionClient = mutableSetOf(
                CarSaleAdPermissionClient.READ,
                CarSaleAdPermissionClient.UPDATE,
                CarSaleAdPermissionClient.DELETE,
                CarSaleAdPermissionClient.MAKE_VISIBLE_OWNER,
                CarSaleAdPermissionClient.MAKE_VISIBLE_PUBLIC
            ),
            yearOfProduction = 2012,
            ownerId = CarSaleUserId("1")
        )

    val AD_SOLD_BOLT1 = AD_ACTIVE_BOLT1.copy(adStatus = CarSaleAdStatus.SOLD)
}