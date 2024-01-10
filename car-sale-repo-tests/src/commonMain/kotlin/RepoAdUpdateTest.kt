package com.github.gerasimovnikita.otus.carsale.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.*
import repo.DbAdRequest
import repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdUpdateTest {
    abstract val repo: IAdRepository
    protected open val updateSucc = initObjects[0]
    protected open val updateConc = initObjects[1]
    private val updateIdNotFound = CarSaleAdId("ad-repo-update-not-found")
    protected val lockBad = CarSaleAdLock("20000000-0000-0000-0000-000000000009")
    protected val lockNew = CarSaleAdLock("20000000-0000-0000-0000-000000000002")

    private val reqUpdateSucc by lazy {
        CarSaleAd(
            id = updateSucc.id,
            carName = "update object",
            description = "update object description",
            ownerId = CarSaleUserId("owner-123"),
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            lock = initObjects.first().lock
        )
    }
    private val reqUpdateNotFound = CarSaleAd(
        id = updateIdNotFound,
        carName = "update object not found",
        description = "update object not found description",
        ownerId = CarSaleUserId("owner-123"),
        visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
        lock = initObjects.first().lock
    )
    private val reqUpdateConc by lazy {
        CarSaleAd(
            id = updateConc.id,
            carName = "update object not found",
            description = "update object not found description",
            ownerId = CarSaleUserId("owner-123"),
            visibility = CarSaleAdVisibility.VISIBLE_PUBLIC,
            adStatus = CarSaleAdStatus.ACTIVE,
            lock = lockBad,
        )
    }

    @Test
    fun updateSuccess() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateSucc))
        assertEquals(true, result.isSuccess)
        assertEquals(reqUpdateSucc.id, result.data?.id)
        assertEquals(reqUpdateSucc.carName, result.data?.carName)
        assertEquals(reqUpdateSucc.description, result.data?.description)
        assertEquals(emptyList(), result.errors)
        assertEquals(lockNew, result.data?.lock)
    }

    @Test
    fun updateNotFound() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateNotFound))
        assertEquals(false, result.isSuccess)
        assertEquals(null, result.data)
        val error = result.errors.find { it.code === "not-found" }
        assertEquals("id", error?.field)
    }


    @Test
    fun updateConcurrencyError() = runRepoTest {
        val result = repo.updateAd(DbAdRequest(reqUpdateConc))
        assertEquals(false, result.isSuccess)
        val error = result.errors.find { it.code == "concurrency" }
        assertEquals("lock", error?.field)
        assertEquals(updateConc, result.data)
    }

    companion object : BaseInitAds("update") {
        override val initObjects: List<CarSaleAd> = listOf(
            createInitTestModel("update"),
            createInitTestModel("updateConc"),
        )
    }
}
