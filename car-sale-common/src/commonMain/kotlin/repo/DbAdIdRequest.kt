package repo

import models.CarSaleAd
import models.CarSaleAdId
import models.CarSaleAdLock


data class DbAdIdRequest(
    val id: CarSaleAdId,
    val lock: CarSaleAdLock = CarSaleAdLock.NONE
) {
    constructor(ad: CarSaleAd): this(ad.id, ad.lock)
}
