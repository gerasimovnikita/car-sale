import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import exceptions.UnknownCarSaleCommand
import models.*

fun CarSaleContext.toTransport(): IResponse = when(val cmd = command) {
    CarSaleCommand.CREATE -> toTransportCreate()
    CarSaleCommand.READ -> toTransportRead()
    CarSaleCommand.UPDATE -> toTransportUpdate()
    CarSaleCommand.DELETE -> toTransportDelete()
    CarSaleCommand.SEARCH -> toTransportSearch()
    CarSaleCommand.OFFERS -> toTransportOffers()
    CarSaleCommand.NONE -> throw UnknownCarSaleCommand(cmd)
}

private fun CarSaleState.getResult(): ResponseResult{
    if(this == CarSaleState.RUNNING) return ResponseResult.SUCCESS
    else return ResponseResult.ERROR
}

fun  CarSaleContext.toTransportInit() = AdInitResponse(
    requestId =this.requestID.asString().takeIf { it.isNotBlank() },
    result = if (errors.isEmpty()) ResponseResult.SUCCESS else ResponseResult.ERROR,
    errors = errors.toTransportErrors()
)

fun CarSaleContext.toTransportCreate() = AdCreateResponse(
    responseType = "create",
    requestId = this.requestID.asString().takeIf { it.isNotBlank() },
    result = state.getResult(),
    errors = errors.toTransportErrors(),
    ad = carSaleResponse.toTransportAd()
)

fun CarSaleContext.toTransportRead() = AdReadResponse(
    responseType = "read",
    requestId = this.requestID.asString().takeIf { it.isNotBlank() },
    result = state.getResult(),
    errors = errors.toTransportErrors(),
    ad = carSaleResponse.toTransportAd()
)

fun CarSaleContext.toTransportUpdate() = AdUpdateResponse(
    responseType = "update",
    requestId = this.requestID.asString().takeIf { it.isNotBlank() },
    result = state.getResult(),
    errors = errors.toTransportErrors(),
    ad = carSaleResponse.toTransportAd()
)

fun CarSaleContext.toTransportDelete() = AdDeleteResponse(
    responseType = "delete",
    requestId = this.requestID.asString().takeIf { it.isNotBlank() },
    result = state.getResult(),
    errors = errors.toTransportErrors(),
    ad = carSaleResponse.toTransportAd()
)

fun CarSaleContext.toTransportSearch() = AdSearchResponse(
    responseType = "search",
    requestId = this.requestID.asString().takeIf { it.isNotBlank() },
    result = state.getResult(),
    errors = errors.toTransportErrors(),
    ads = carSaleAdsResponse.toTransportAd()
)

fun CarSaleContext.toTransportOffers() = AdOffersResponse(
    responseType = "offers",
    requestId = this.requestID.asString().takeIf { it.isNotBlank() },
    result = state.getResult(),
    errors = errors.toTransportErrors(),
    ads = carSaleAdsResponse.toTransportAd()
)

private  fun List<CarSaleAd>.toTransportAd(): List<AdResponseObject>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CarSaleAd.toTransportAd(): AdResponseObject = AdResponseObject(
    id = id.takeIf { it != CarSaleAdId.NONE }?.asString(),
    carName = carName.takeIf { it.isNotBlank() },
    description = description.takeIf { it.isNotBlank() },
    yearOfProduction = yearOfProduction.takeIf { it in 1901..2022 }.toString(),
    milage = milage.takeIf { it in 0..999999 }.toString(),
    adStatus = adStatus.toTransportAd(),
    ownerId = ownerId.takeIf { it != CarSaleUserId.NONE }?.asString(),
    visibility = visibility.toTransportAd(),
    permissions = permissionClient.toTransportAd(),
)

private fun List<CarSaleError>.toTransportErrors(): List<Error>? = this
    .map { it.toTransportAd() }
    .toList()
    .takeIf { it.isNotEmpty() }

private fun CarSaleError.toTransportAd() = Error(
    code = code.takeIf { it.isNotBlank() },
    group = group.takeIf { it.isNotBlank() },
    field = field.takeIf { it.isNotBlank() },
    message = message.takeIf { it.isNotBlank() },
)

private fun CarSaleAdVisibility.toTransportAd(): AdVisibility? = when (this) {
    CarSaleAdVisibility.VISIBLE_PUBLIC -> AdVisibility.PUBLIC
    CarSaleAdVisibility.VISIBLE_TO_OWNER -> AdVisibility.OWNER_ONLY
    CarSaleAdVisibility.NONE -> null
}

private fun CarSaleAdStatus.toTransportAd(): AdStatus? = when (this){
    CarSaleAdStatus.ACTIVE -> AdStatus.ACTIVE
    CarSaleAdStatus.SOLD -> AdStatus.SOLD
    CarSaleAdStatus.NONE -> null
}

private fun Set<CarSaleAdPermissionClient>.toTransportAd(): Set<AdPermissions>? = this
    .map { it.toTransportAd() }
    .toSet()
    .takeIf { it.isNotEmpty() }

private fun CarSaleAdPermissionClient.toTransportAd(): AdPermissions = when(this){
    CarSaleAdPermissionClient.UPDATE -> AdPermissions.UPDATE
    CarSaleAdPermissionClient.READ -> AdPermissions.READ
    CarSaleAdPermissionClient.DELETE -> AdPermissions.DELETE
    CarSaleAdPermissionClient.MAKE_VISIBLE_OWNER -> AdPermissions.MAKE_VISIBLE_OWN
    CarSaleAdPermissionClient.MAKE_VISIBLE_PUBLIC -> AdPermissions.MAKE_VISIBLE_PUBLIC
}