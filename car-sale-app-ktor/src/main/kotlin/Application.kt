package com.github.gerasimovnikita.otus.carsale.app.ktor

import carsale.marketplace.api.apiMapper
import com.github.gerasimovnikita.otus.carsale.app.ktor.v1.CarSaleAd
import com.github.gerasimovnikita.otus.carsale.app.ktor.v1.wsHandler
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import io.ktor.http.*
import io.ktor.serialization.jackson.*
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
import org.slf4j.event.Level

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    val processor = CarSaleAdProcessor()

    install(CachingHeaders)
    install(DefaultHeaders)
    install(AutoHeadResponse)
    install(WebSockets)

    install(CORS) {
        allowMethod(HttpMethod.Options)
        allowMethod(HttpMethod.Put)
        allowMethod(HttpMethod.Delete)
        allowMethod(HttpMethod.Patch)
        allowHeader(HttpHeaders.Authorization)
        allowHeader("MyCustomHeader")
        allowCredentials = true
        anyHost() // TODO remove
    }

    install(CallLogging) {
        level = Level.INFO
    }

    @Suppress("OPT_IN_USAGE")
    install(Locations)

    routing {
        get("/") {
            call.respondText("Hello, world!")
        }
        route("/api/v1"){
            install(ContentNegotiation){
                jackson {
                    setConfig(apiMapper.serializationConfig)
                    setConfig(apiMapper.deserializationConfig)
                }
            }

            CarSaleAd(processor)
        }

        webSocket("/ws/v1"){
            wsHandler(processor)
        }

        static("static"){
            resources("static")
        }
    }
}