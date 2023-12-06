package com.github.gerasimovnikita.otus.carsale.app.ktor.v1

import CarSaleContext
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import fromTransport
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import toTransportCreate
import toTransportDelete
import toTransportRead
import toTransportSearch
import toTransportUpdate

suspend fun ApplicationCall.createCarSaleAd(processor: CarSaleAdProcessor){
    val request = receive<AdCreateRequest>()
    val context = CarSaleContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportCreate())
}

suspend fun ApplicationCall.readCarSaleAd(processor: CarSaleAdProcessor){
    val request = receive<AdReadRequest>()
    val context = CarSaleContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportRead())
}

suspend fun ApplicationCall.updateCarSaleAd(processor: CarSaleAdProcessor){
    val request = receive<AdUpdateRequest>()
    val context = CarSaleContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportUpdate())
}

suspend fun ApplicationCall.deleteCarSaleAd(processor: CarSaleAdProcessor){
    val request = receive<AdDeleteRequest>()
    val context = CarSaleContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportDelete())
}

suspend fun ApplicationCall.searchCarSaleAd(processor: CarSaleAdProcessor){
    val request = receive<AdSearchRequest>()
    val context = CarSaleContext()
    context.fromTransport(request)
    processor.exec(context)
    respond(context.toTransportSearch())
}