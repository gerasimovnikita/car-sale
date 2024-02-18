package com.github.gerasimovnikita.otus.carsale.app.ktor.v1

import com.github.gersimovnikita.otus.carsale.app.common.CarSaleAppSettings
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.CarSaleAd(appSettings: CarSaleAppSettings){
    route("car-sale-ad"){
        post("create") {
            call.createCarSaleAd(appSettings)
        }
        post("read"){
            call.readCarSaleAd(appSettings)
        }
        post("update"){
            call.updateCarSaleAd(appSettings)
        }
        post("delete"){
            call.deleteCarSaleAd(appSettings)
        }
        post("search"){
            call.searchCarSaleAd(appSettings)
        }
    }
}
