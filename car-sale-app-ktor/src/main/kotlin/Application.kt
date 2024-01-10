package com.github.gerasimovnikita.otus.carsale.app.ktor

import carsale.marketplace.api.apiMapper
import com.github.gerasimovnikita.otus.carsale.app.ktor.plugins.initAppSettings
import com.github.gerasimovnikita.otus.carsale.app.ktor.plugins.initPlugins
import com.github.gerasimovnikita.otus.carsale.app.ktor.v1.CarSaleAd
import com.github.gerasimovnikita.otus.carsale.app.ktor.v1.WsHandler
import com.github.gersimovnikita.otus.carsale.app.common.CarSaleAppSettings
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.locations.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.autohead.*
import io.ktor.server.plugins.cachingheaders.*
import io.ktor.server.plugins.callloging.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.plugins.defaultheaders.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.websocket.*
import models.CarSaleAd
import org.slf4j.event.Level

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(appSettings: CarSaleAppSettings = initAppSettings()) {
    initPlugins(appSettings)
    val handler = WsHandler()

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("/api/v1") {
            install(ContentNegotiation) {
                jackson {
                    setConfig(apiMapper.serializationConfig)
                    setConfig(apiMapper.deserializationConfig)
                }
            }

            CarSaleAd(appSettings)
        }

        webSocket("/ws/v1") {
            handler.handle(this, appSettings)
        }
    }
}