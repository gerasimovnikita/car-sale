package com.github.gerasimovnikita.otus.carsale.biz.tests.repo

import CarSaleContext
import CarSaleCorSettings
import com.github.gerasimovnikita.otus.carsale.biz.CarSaleAdProcessor
import com.github.gerasimovnikita.otus.carsale.repo.tests.AdRepositoryMock
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import models.*
import repo.DbAdResponse
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val userId = CarSaleUserId("321")
    private val command = CarSaleCommand.UPDATE
    private val initAd = CarSaleAd(
        id = CarSaleAdId("123"),
        carName = "abc",
        description = "abc",
        ownerId = userId,
        adStatus = CarSaleAdStatus.ACTIVE,
        visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepositoryMock(
        invokeReadAd = {
            DbAdResponse(
                isSuccess = true,
                data = initAd,
            )
        },
        invokeUpdateAd = {
            DbAdResponse(
                isSuccess = true,
                data = CarSaleAd(
                    id = CarSaleAdId("123"),
                    carName = "xyz",
                    description = "xyz",
                    adStatus = CarSaleAdStatus.ACTIVE,
                    visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
                )
            )
        }
    ) }
    private val settings by lazy {
        CarSaleCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { CarSaleAdProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = CarSaleAd(
            id = CarSaleAdId("123"),
            carName = "xyz",
            description = "xyz",
            adStatus = CarSaleAdStatus.ACTIVE,
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = CarSaleAdLock("123-234-abc-ABC"),
        )
        val ctx = CarSaleContext(
            command = command,
            state = CarSaleState.NONE,
            workMode = CarSaleWorkMode.TEST,
            carSaleRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(CarSaleState.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.carSaleResponse.id)
        assertEquals(adToUpdate.carName, ctx.carSaleResponse.carName)
        assertEquals(adToUpdate.description, ctx.carSaleResponse.description)
        assertEquals(adToUpdate.adStatus, ctx.carSaleResponse.adStatus)
        assertEquals(adToUpdate.visibility, ctx.carSaleResponse.visibility)
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(command)
}
