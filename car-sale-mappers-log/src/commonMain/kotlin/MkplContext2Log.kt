import com.github.gerasimovnikita.otus.carsale.api.log.models.*
import kotlinx.datetime.Clock
import models.*

fun CarSaleContext.toLog(logId: String) = CommonLogModel(
    messageTime = Clock.System.now().toString(),
    logId = logId,
    source = "car-sale",
    ad = toMkplLog(),
    errors = errors.map { it.toLog() },
)

fun CarSaleContext.toMkplLog(): CarSaleLogModel? {
    val adNone = CarSaleAd()
    return CarSaleLogModel(
        requestId = requestID.takeIf { it != CarSaleRequestId.NONE }?.asString(),
        requestAd = carSaleRequest.takeIf { it != adNone }?.toLog(),
        responseAd = carSaleResponse.takeIf { it != adNone }?.toLog(),
        responseAds = carSaleAdsResponse.takeIf { it.isNotEmpty() }?.filter { it != adNone }?.map { it.toLog() },
        requestFilter = carSaleAdFilterRequest.takeIf { it != CarSaleAdFilter() }?.toLog(),
    ).takeIf { it != CarSaleLogModel() }
}

private fun CarSaleAdFilter.toLog() = AdFilterLog(
    searchString = searchString.takeIf { it.isNotBlank() },
    ownerId = ownerId.takeIf { it != CarSaleUserId.NONE }?.asString()
)

fun CarSaleError.toLog() = ErrorLogModel(
    message = message.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    code = code.takeIf { it.isNotBlank() },
    level = level.name,
)

fun CarSaleAd.toLog() = AdLog(
    id = id.takeIf { it != CarSaleAdId.NONE }?.asString(),
    carName = carName.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    visibility = visibility.takeIf { it != CarSaleAdVisibility.NONE }?.name,
    ownerId = ownerId.takeIf { it != CarSaleUserId.NONE }?.asString()
)
