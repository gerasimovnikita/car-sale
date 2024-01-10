import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import exceptions.UnknownRequestClass
import models.*
import stubs.CarSaleStubs

fun CarSaleContext.fromTransport(request: IRequest) = when(request) {
    is AdCreateRequest -> fromTransport(request)
    is AdReadRequest -> fromTransport(request)
    is AdUpdateRequest -> fromTransport(request)
    is AdDeleteRequest -> fromTransport(request)
    is AdSearchRequest -> fromTransport(request)
    is AdOffersRequest -> fromTransport(request)
    else -> throw  UnknownRequestClass(request.javaClass)
}

private fun String?.toAdId() = this?.let { CarSaleAdId(it) } ?: CarSaleAdId.NONE
private fun String?.toAdWithId() = CarSaleAd(id = this.toAdId())
private fun String?.toAdLock() = this?.let { CarSaleAdLock(it) } ?: CarSaleAdLock.NONE
private fun IRequest?.requestId() = this?.requestId?.let { CarSaleRequestId(it) } ?: CarSaleRequestId.NONE

private fun AdDebug?.transportToWorkMode(): CarSaleWorkMode = when(this?.mode){
    AdRequestDebugMode.PROD -> CarSaleWorkMode.PROD
    AdRequestDebugMode.TEST -> CarSaleWorkMode.TEST
    AdRequestDebugMode.STUB -> CarSaleWorkMode.STUB
    null -> CarSaleWorkMode.PROD
}

private fun AdDebug?.transportToStubCase(): CarSaleStubs = when(this?.stub){
    AdRequestDebugStubs.SUCCESS -> CarSaleStubs.SUCCESS
    AdRequestDebugStubs.NOT_FOUND -> CarSaleStubs.NOT_FOUND
    AdRequestDebugStubs.BAD_ID -> CarSaleStubs.BAD_ID
    AdRequestDebugStubs.BAD_TITLE -> CarSaleStubs.BAD_CAR_NAME
    AdRequestDebugStubs.BAD_DESCRIPTION -> CarSaleStubs.BAD_DESCRIPTION
    AdRequestDebugStubs.BAD_VISIBILITY -> CarSaleStubs.BAD_VISIBILITY
    AdRequestDebugStubs.CANNOT_DELETE -> CarSaleStubs.CANNOT_DELETE
    AdRequestDebugStubs.BAD_SEARCH_STRING -> CarSaleStubs.BAD_SEARCH_STRING
    null -> CarSaleStubs.NONE
}

fun CarSaleContext.fromTransport(request: AdCreateRequest){
    command = CarSaleCommand.CREATE
    requestID = request.requestId()
    carSaleRequest = request.ad?.toInternal() ?: CarSaleAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CarSaleContext.fromTransport(request: AdReadRequest){
    command = CarSaleCommand.READ
    requestID = request.requestId()
    carSaleRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CarSaleContext.fromTransport(request: AdUpdateRequest){
    command = CarSaleCommand.UPDATE
    requestID = request.requestId()
    carSaleRequest = request.ad?.toInternal() ?: CarSaleAd()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CarSaleContext.fromTransport(request: AdDeleteRequest){
    command = CarSaleCommand.DELETE
    requestID = request.requestId()
    carSaleRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}
private fun AdDeleteObject?.toInternal(): CarSaleAd = if (this != null) {
    CarSaleAd(
        id = id.toAdId(),
        lock = lock.toAdLock(),
    )
} else {
    CarSaleAd.NONE
}


fun CarSaleContext.fromTransport(request: AdSearchRequest){
    command = CarSaleCommand.SEARCH
    requestID = request.requestId()
    carSaleAdFilterRequest = request.adFilter.toInternal()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

fun CarSaleContext.fromTransport(request: AdOffersRequest){
    command = CarSaleCommand.OFFERS
    requestID = request.requestId()
    carSaleRequest = request.ad?.id.toAdWithId()
    workMode = request.debug.transportToWorkMode()
    stubCase = request.debug.transportToStubCase()
}

private fun AdCreateObject.toInternal(): CarSaleAd = CarSaleAd(
    carName = this.carName?:"",
    description = this.description?:"",
    yearOfProduction = this.yearOfProduction?.toInt() ?: 1977,
    milage = this.milage?.toInt()?:0,
    adStatus = this.adStatus.fromTransport(),
    visibility = this.visibility.fromTransport()
)

private fun AdUpdateObject.toInternal(): CarSaleAd = CarSaleAd(
    carName = this.carName?:"",
    description = this.description?:"",
    yearOfProduction = this.yearOfProduction?.toInt() ?: 1977,
    milage = this.milage?.toInt()?:0,
    adStatus = this.adStatus.fromTransport(),
    visibility = this.visibility.fromTransport(),
    lock = lock.toAdLock(),
)

private fun AdSearchFilter?.toInternal(): CarSaleAdFilter = CarSaleAdFilter(
    searchString = this?.searchString ?: ""
)

private fun AdStatus?.fromTransport(): CarSaleAdStatus = when(this) {
    AdStatus.ACTIVE -> CarSaleAdStatus.ACTIVE
    AdStatus.SOLD -> CarSaleAdStatus.SOLD
    null -> CarSaleAdStatus.NONE
}

private fun AdVisibility?.fromTransport(): CarSaleAdVisibility = when(this){
    AdVisibility.PUBLIC -> CarSaleAdVisibility.VISIBLE_PUBLIC
    AdVisibility.OWNER_ONLY -> CarSaleAdVisibility.VISIBLE_TO_OWNER
    null -> CarSaleAdVisibility.NONE
}