package repo

import models.CarSaleAd
import models.CarSaleError

data class DbAdsResponse(
    override val data: List<CarSaleAd>?,
    override val isSuccess: Boolean,
    override val errors: List<CarSaleError> = emptyList(),
): IDbResponse<List<CarSaleAd>> {

    companion object {
        val MOCK_SUCCESS_EMPTY = DbAdsResponse(emptyList(), true)
        fun success(result: List<CarSaleAd>) = DbAdsResponse(result, true)
        fun error(errors: List<CarSaleError>) = DbAdsResponse(null, false, errors)
        fun error(error: CarSaleError) = DbAdsResponse(null, false, listOf(error))
    }
}
