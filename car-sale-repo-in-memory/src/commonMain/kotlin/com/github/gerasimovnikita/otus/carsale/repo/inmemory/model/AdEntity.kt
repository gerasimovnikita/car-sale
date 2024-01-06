package com.github.gerasimovnikita.otus.carsale.repo.inmemory.model

import models.CarSaleAd
import models.CarSaleAdId
import models.CarSaleAdVisibility
import models.CarSaleUserId

data class AdEntity(
    val id: String? = null,
    val carName: String? = null,
    val description: String? = null,
    val ownerId: String? = null,
    val visibility: String? = null,
) {
    constructor(model: CarSaleAd): this(
        id = model.id.asString().takeIf { it.isNotBlank() },
        carName = model.carName.takeIf { it.isNotBlank() },
        description = model.description.takeIf { it.isNotBlank() },
        ownerId = model.ownerId.asString().takeIf { it.isNotBlank() },
        visibility = model.visibility.takeIf { it != CarSaleAdVisibility.NONE }?.name,
    )

    fun toInternal() = CarSaleAd(
        id = id?.let { CarSaleAdId(it) }?: CarSaleAdId.NONE,
        carName = carName?: "",
        description = description?: "",
        ownerId = ownerId?.let { CarSaleUserId(it) }?: CarSaleUserId.NONE,
        visibility = visibility?.let { CarSaleAdVisibility.valueOf(it) }?: CarSaleAdVisibility.NONE,
    )
}
