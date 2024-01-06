package com.github.gerasimovnikita.otus.carsale.repo.tests

import models.*

abstract class BaseInitAds(val op: String): IInitObjects<CarSaleAd> {

    fun createInitTestModel(
        suf: String,
        ownerId: CarSaleUserId = CarSaleUserId("owner-123")
    ) = CarSaleAd(
        id = CarSaleAdId("ad-repo-$op-$suf"),
        carName = "$suf car name stub",
        description = "$suf stub description",
        ownerId = ownerId,
        visibility = CarSaleAdVisibility.VISIBLE_TO_OWNER
    )
}
