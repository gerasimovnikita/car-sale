package com.github.gerasimovnikita.otus.carsale.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.CarSaleAd
import models.CarSaleAdId
import models.CarSaleAdVisibility
import models.CarSaleUserId
import repo.DbAdRequest
import repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdCreateTest {
    abstract val repo: IAdRepository

    private val createObj = CarSaleAd(
        carName = "create object",
        description = "create object description",
        ownerId = CarSaleUserId("owner-123"),
        visibility = CarSaleAdVisibility.VISIBLE_PUBLIC
    )

    @Test
    fun createSuccess() = runRepoTest {
        val result = repo.createAd(DbAdRequest(createObj))
        val expected = createObj.copy(id = result.data?.id ?: CarSaleAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.carName, result.data?.carName)
        assertEquals(expected.description, result.data?.description)
        assertNotEquals(CarSaleAdId.NONE, result.data?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object : BaseInitAds("create") {
        override val initObjects: List<CarSaleAd> = emptyList()
    }
}
