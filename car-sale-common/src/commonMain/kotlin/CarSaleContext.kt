import kotlinx.datetime.Instant
import models.*
import stubs.CarSaleStubs

data class CarSaleContext(
    var command: CarSaleCommand = CarSaleCommand.NONE,
    var state: CarSaleState = CarSaleState.NONE,
    val errors: MutableList<CarSaleError> = mutableListOf(),
    var workMode: CarSaleWorkMode = CarSaleWorkMode.PROD,
    var stubCase: CarSaleStubs = CarSaleStubs.NONE,
    var requestID: CarSaleRequestId = CarSaleRequestId.NONE,
    var timeStart: Instant = Instant.NONE,
    var carSaleRequest: CarSaleAd = CarSaleAd(),
    var carSaleAdValidating: CarSaleAd = CarSaleAd(),
    var carSaleAdValidated: CarSaleAd = CarSaleAd(),
    var carSaleAdFilterRequest: CarSaleAdFilter = CarSaleAdFilter(),
    var carSaleAdFilterValidating: CarSaleAdFilter = CarSaleAdFilter(),
    var carSaleAdFilterValidated: CarSaleAdFilter = CarSaleAdFilter(),
    var carSaleResponse: CarSaleAd = CarSaleAd(),
    val carSaleAdsResponse: MutableList<CarSaleAd> = mutableListOf(),
    var settings: CarSaleCorSettings = CarSaleCorSettings.NONE
)