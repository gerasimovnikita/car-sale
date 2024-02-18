package repo

import helpers.errorRepoConcurrency
import models.CarSaleAd
import models.CarSaleAdLock
import models.CarSaleError
import helpers.errorEmptyId as carSaleErrorEmptyId
import helpers.errorNotFound as carSaelErrorNotFound


data class DbAdResponse(
    override val data: CarSaleAd?,
    override val isSuccess: Boolean,
    override val errors: List<CarSaleError> = emptyList()
): IDbResponse<CarSaleAd> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        fun success(result: CarSaleAd) = DbAdResponse(result, true)
        fun error(errors: List<CarSaleError>) = DbAdResponse(null, false, errors)
        fun error(error: CarSaleError, @Suppress("UNUSED_VARIABLE") data: CarSaleAd? = null) = DbAdResponse(null, false, listOf(error))

        val errorEmptyId = error(carSaleErrorEmptyId)

        fun errorConcurrent(lock: CarSaleAdLock, ad: CarSaleAd?) = error(
            errorRepoConcurrency(lock, ad?.lock?.let { CarSaleAdLock(it.asString()) }),
            ad
        )

        val errorNotFound = error(carSaelErrorNotFound)
    }
}
