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


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository
    protected open val updateSucc = initObjects[0]
    private val updateIdNotFound = CarSaleAdId("ad-repo-update-not-found")

    private val reqUpdateSucc by lazy {
        CarSaleAd(
            id = updateSucc.id,
            carName = "update object",
            description = "update object description",
            ownerId = CarSaleUserId("owner-123"),
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC
        )
    }
    private val reqUpdateNotFound = CarSaleAd(
        id = updateIdNotFound,
        carName = "update object not found",
        description = "update object not found description",
        ownerId = CarSaleUserId("owner-123"),
        visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
    )

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.carName, result.data?.carName)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(emptyList(), result.errors)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code === "not-found" }
        assertEquals("id", error?.field)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<CarSaleAd> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
