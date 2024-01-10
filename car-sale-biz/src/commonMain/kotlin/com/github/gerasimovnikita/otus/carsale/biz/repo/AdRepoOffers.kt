package com.github.gerasimovnikita.otus.carsale.biz.repo

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import models.CarSaleError
import models.CarSaleState
import repo.DbAdFilterRequest
import repo.DbAdsResponse

fun ICorChainDsl<CarSaleContext>.repoOffers(title: String) = worker {
    this.title = title
    description = "Поиск предложений для объявления по названию"
    on { state == CarSaleState.RUNNING }
    handle {
        val adRequest = adRepoPrepare
        val filter = DbAdFilterRequest(
            titleFilter = adRequest.carName
        )
        val dbResponse = if (filter.state == CarSaleState.NONE) {
            DbAdsResponse(
                data = null,
                isSuccess = false,
                errors = listOf(
                    CarSaleError(
                        field = "adType",
                        message = "Type of ad must not be empty"
                    )
                )
            )
        } else {
            adRepo.searchAd(filter)
        }

        val resultAds = dbResponse.data
        when {
            !resultAds.isNullOrEmpty() -> adsRepoDone = resultAds.toMutableList()
            dbResponse.isSuccess -> return@handle
            else -> {
                state = CarSaleState.FAILING
                errors.addAll(dbResponse.errors)
            }
        }
    }
}
