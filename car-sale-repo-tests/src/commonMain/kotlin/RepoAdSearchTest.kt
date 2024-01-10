package com.github.gerasimovnikita.otus.carsale.repo.tests

import kotlinx.coroutines.ExperimentalCoroutinesApi
import models.CarSaleAd
import models.CarSaleState
import models.CarSaleUserId
import repo.DbAdFilterRequest
import repo.IAdRepository
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
abstract class RepoAdSearchTest {
    abstract val repo: IAdRepository

    protected open val initializedObjects: List<CarSaleAd> = initObjects

    @Test
    fun searchOwner() = runRepoTest {
        val result = repo.searchAd(DbAdFilterRequest(ownerId = searchOwnerId))
        assertEquals(true, result.isSuccess)
        val expected = listOf(initializedObjects[1], initializedObjects[2]).sortedBy { it.id.asString() }
        assertEquals(expected, result.data?.sortedBy { it.id.asString() })
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitAds("search") {

        val searchOwnerId = CarSaleUserId("owner-124")
        override val initObjects: List<CarSaleAd> = listOf(
            createInitTestModel("ad1"),
            createInitTestModel("ad2", ownerId = searchOwnerId),
            createInitTestModel("ad4", ownerId = searchOwnerId)
        )
    }
}
