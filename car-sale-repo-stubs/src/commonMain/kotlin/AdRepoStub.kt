package com.github.gerasimovnikita.otus.carsale.repository.inmemory

import CarSaleAdStub
import models.CarSaleAdStatus
import repo.*


class AdRepoStub() : IAdRepository {
    override suspend fun createAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = CarSaleAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun readAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = CarSaleAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun updateAd(rq: DbAdRequest): DbAdResponse {
        return DbAdResponse(
            data = CarSaleAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun deleteAd(rq: DbAdIdRequest): DbAdResponse {
        return DbAdResponse(
            data = CarSaleAdStub.prepareResult {  },
            isSuccess = true,
        )
    }

    override suspend fun searchAd(rq: DbAdFilterRequest): DbAdsResponse {
        return DbAdsResponse(
            data = CarSaleAdStub.prepareSearchList(filter = "", CarSaleAdStatus.NONE),
            isSuccess = true,
        )
    }
}
