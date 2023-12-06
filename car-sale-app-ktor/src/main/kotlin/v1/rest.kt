package com.github.gerasimovnikita.otus.carsale.app.ktor.v1

import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Route.CarSaleAd(processor: CarSaleAdProcessor){
    route("car-sale-ad"){
        post("create") {
            call.createCarSaleAd(processor)
        }
        post("read"){
            call.readCarSaleAd(processor)
        }
        post("update"){
            call.updateCarSaleAd(processor)
        }
        post("delete"){
            call.deleteCarSaleAd(processor)
        }
        post("search"){
            call.searchCarSaleAd(processor)
        }
    }
}
