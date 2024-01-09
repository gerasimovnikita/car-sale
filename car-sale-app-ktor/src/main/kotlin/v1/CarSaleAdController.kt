package com.github.gerasimovnikita.otus.carsale.app.ktor.v1

import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gersimovnikita.otus.carsale.app.common.CarSaleAppSettings
import fromTransport
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate

suspend fun ApplicationCall.createCarSaleAd(appSettings: CarSaleAppSettings) : Unit =
    processRequest<AdCreateRequest, AdCreateResponse>(appSettings, ApplicationCall::createCarSaleAd::class, "car-sale-ad-create")

suspend fun ApplicationCall.readCarSaleAd(appSettings: CarSaleAppSettings) : Unit =
    processRequest<AdReadRequest, AdReadResponse>(appSettings, ApplicationCall::readCarSaleAd::class, "car-sale-ad-read")

suspend fun ApplicationCall.updateCarSaleAd(appSettings: CarSaleAppSettings) : Unit =
    processRequest<AdUpdateRequest, AdUpdateResponse>(appSettings, ApplicationCall::updateCarSaleAd::class, "car-sale-ad-update")

suspend fun ApplicationCall.deleteCarSaleAd(appSettings: CarSaleAppSettings) : Unit =
    processRequest<AdDeleteRequest, AdDeleteResponse>(appSettings, ApplicationCall::deleteCarSaleAd::class, "car-sale-ad-delete")

suspend fun ApplicationCall.searchCarSaleAd(appSettings: CarSaleAppSettings) :Unit =
    processRequest<AdSearchRequest, AdSearchResponse>(appSettings, ApplicationCall::searchCarSaleAd::class, "car-sale-ad-search")