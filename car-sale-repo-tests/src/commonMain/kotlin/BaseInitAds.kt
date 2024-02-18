package com.github.gerasimovnikita.otus.carsale.repo.tests

import models.*

abstract class BaseInitAds(val op: String): IInitObjects<CarSaleAd> {

    open val lockOld: CarSaleAdLock = CarSaleAdLock("20000000-0000-0000-0000-000000000001")
    open val lockBad: CarSaleAdLock = CarSaleAdLock("20000000-0000-0000-0000-000000000009")
    fun createInitTestModel(
        suf: String,
        ownerId: CarSaleUserId = CarSaleUserId("owner-123") ,
        lock: CarSaleAdLock = lockOld,
    ) = CarSaleAd(
        id = CarSaleAdId("ad-repo-$op-$suf"),
        carName = "$suf car name stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = CarSaleAdVisibility.VISIBLE_TO_OWNER,
        lock = lock
    )
}
