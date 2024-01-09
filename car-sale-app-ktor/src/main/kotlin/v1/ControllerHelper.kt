package com.github.gerasimovnikita.otus.carsale.app.ktor.v1

import com.github.gerasimovnikita.otus.carsale.api.v1.models.IRequest
import com.github.gerasimovnikita.otus.carsale.api.v1.models.IResponse
import com.github.gersimovnikita.otus.carsale.app.common.CarSaleAppSettings
import fromTransport
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import ru.otus.otuskotlin.marketplace.app.common.process
import toTransport
import kotlin.reflect.KClass

suspend inline fun <reified Q : IRequest, @Suppress("unused") reified R : IResponse> ApplicationCall.processRequest(
    appSettings: CarSaleAppSettings,
    klass: KClass<*>,
    logId: String,
) {
    appSettings.processor.process(appSettings.logger.logger(klass), logId,
        fromTransport = {
            val request = receive<Q>()
            fromTransport(request)
        },
        sendResponse = { respond(toTransport()) }
    )
}
