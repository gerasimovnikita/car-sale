package repo

import models.CarSaleAd
import models.CarSaleError


data class DbAdResponse(
    override val data: CarSaleAd?,
    override val isSuccess: Boolean,
    override val errors: List<CarSaleError> = emptyList()
): IDbResponse<CarSaleAd> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdResponse(null, true)
        fun success(result: CarSaleAd) = DbAdResponse(result, true)
        fun error(errors: List<CarSaleError>) = DbAdResponse(null, false, errors)
        fun error(error: CarSaleError) = DbAdResponse(null, false, listOf(error))
    }
}
