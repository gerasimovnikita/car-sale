import CarSaleAdStubBolts.AD_ACTIVE_BOLT1
import models.CarSaleAd
import models.CarSaleAdId
import models.CarSaleAdStatus

object CarSaleAdStub {
    fun get(): CarSaleAd = AD_ACTIVE_BOLT1.copy()

    fun prepareSearchList(filter: String, status: CarSaleAdStatus) = listOf(
        carSaleActiveAd("111", filter, status),
        carSaleActiveAd("222", filter, status),
        carSaleActiveAd("333", filter, status),
        carSaleActiveAd("444", filter, status),
        carSaleActiveAd("555", filter, status),
        carSaleActiveAd("666", filter, status)
    )

    fun prepareResult(block: CarSaleAd.() -> Unit): CarSaleAd = get().apply(block)

    private fun carSaleActiveAd(id: String, filter: String, status: CarSaleAdStatus) =
        carSaleAd(AD_ACTIVE_BOLT1, id = id, filter = filter, status = status)

    private fun carSaleAd(base: CarSaleAd, id: String, filter: String, status: CarSaleAdStatus) = base.copy(
        id= CarSaleAdId(id),
        carName = "$filter $id",
        description = "desc $filter $id",
        adStatus = status
    )
}