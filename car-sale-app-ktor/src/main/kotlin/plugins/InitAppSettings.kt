package com.github.gerasimovnikita.otus.carsale.app.ktor.plugins

import CarSaleCorSettings
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gerasimovnikita.otus.carsale.repo.inmemory.AdRepoInMemory
import com.github.gerasimovnikita.otus.carsale.repository.inmemory.AdRepoStub
import com.github.gersimovnikita.otus.carsale.app.common.CarSaleAppSettings
import com.github.gersimovnikita.otus.carsale.logging.common.CarSaleLoggerProvider
//import com.github.gersimovnikita.otus.carsale.logging.jvm.mpLoggerLogback
import io.ktor.server.application.*
import ru.otus.otuskotlin.marketplace.logging.kermit.mpLoggerKermit

fun Application.initAppSettings(): CarSaleAppSettings {
    val corSettings = CarSaleCorSettings(
        loggerProvider = getLoggerProviderConf(),
        repoTest = AdRepoInMemory(),
        repoStub = AdRepoStub()
    )
    return CarSaleAppSettings(
        appUrls = environment.config.propertyOrNull("ktor.urls")?.getList() ?: emptyList(),
        processor = CarSaleAdProcessor(corSettings),
        logger = getLoggerProviderConf(),
    )
}
//expect fun Application.getLoggerProviderConf(): CarSaleLoggerProvider
fun Application.getLoggerProviderConf(): CarSaleLoggerProvider =
    when (val mode = environment.config.propertyOrNull("ktor.logger")?.getString()) {
        "kmp" -> CarSaleLoggerProvider { mpLoggerKermit(it) }
//        "logback", null -> CarSaleLoggerProvider { mpLoggerLogback(it) }
        else -> throw Exception("Logger $mode is not allowed. Admitted values are kmp and logback")
    }