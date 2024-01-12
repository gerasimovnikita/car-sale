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
import kotlin.test.assertTrue

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoDeleteTest {

    private val userId = CarSaleUserId("321")
    private val command = CarSaleCommand.DELETE
    private val initAd = CarSaleAd(
        id = CarSaleAdId("123"),
        carName = "abc",
        description = "abc",
        ownerId = userId,
        adStatus = CarSaleAdStatus.ACTIVE,
        visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
        lock = CarSaleAdLock("123-234-abc-ABC"),
    )
    private val repo by lazy {
        AdRepositoryMock(
            invokeReadAd = {
               DbAdResponse(
                   isSuccess = true,
                   data = initAd,
               )
            },
            invokeDeleteAd = {
                if (it.id == initAd.id)
                    DbAdResponse(
                        isSuccess = true,
                        data = initAd
                    )
                else DbAdResponse(isSuccess = false, data = null)
            }
        )
    }
    private val settings by lazy {
        CarSaleCorSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { CarSaleAdProcessor(settings) }

    @Test
    fun repoDeleteSuccessTest() = runTest {
        val adToUpdate = CarSaleAd(
            id = CarSaleAdId("123"),
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
        assertTrue { ctx.errors.isEmpty() }
        assertEquals(initAd.id, ctx.carSaleResponse.id)
        assertEquals(initAd.carName, ctx.carSaleResponse.carName)
        assertEquals(initAd.description, ctx.carSaleResponse.description)
        assertEquals(initAd.adStatus, ctx.carSaleResponse.adStatus)
        assertEquals(initAd.visibility, ctx.carSaleResponse.visibility)
    }

    @Test
    fun repoDeleteNotFoundTest() = repoNotFoundTest(command)
}
