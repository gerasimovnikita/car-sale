package repo

import models.CarSaleAd
import models.CarSaleAdId


data class DbAdIdRequest(
    val id: CarSaleAdId
) {
    constructor(ad: CarSaleAd): this(ad.id)
}
