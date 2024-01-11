package com.github.gerasimovnikita.otus.carsale.biz.groups

import CarSaleContext
import car.sale.cor.ICorChainDsl
import car.sale.cor.worker
import helpers.errorAdministration
import helpers.fail
import models.CarSaleWorkMode
import repo.IAdRepository

fun ICorChainDsl<CarSaleContext>.initRepo(title: String) = worker {
    this.title = title
    description = """
        Вычисление основного рабочего репозитория в зависимости от зпрошенного режима работы        
    """.trimIndent()
    handle {
        adRepo = when {
            workMode == CarSaleWorkMode.TEST -> settings.repoTest
            workMode == CarSaleWorkMode.STUB -> settings.repoStub
            else -> settings.repoProd
        }
        if (workMode != CarSaleWorkMode.STUB && adRepo == IAdRepository.NONE) fail(
            errorAdministration(
                field = "repo",
                violationCode = "dbNotConfigured",
                description = "The database is unconfigured for chosen workmode ($workMode). " +
                        "Please, contact the administrator staff"
            )
        )
    }
}
