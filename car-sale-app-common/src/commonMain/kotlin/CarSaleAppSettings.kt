package com.github.gersimovnikita.otus.carsale.app.common

import CarSaleCorSettings
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gersimovnikita.otus.carsale.logging.common.CarSaleLoggerProvider

data class CarSaleAppSettings(
    val appUrls: List<String> = emptyList(),
    val corSettings: CarSaleCorSettings = CarSaleCorSettings(),
    val processor: CarSaleAdProcessor = CarSaleAdProcessor(),
    val logger: CarSaleLoggerProvider = CarSaleLoggerProvider()
)