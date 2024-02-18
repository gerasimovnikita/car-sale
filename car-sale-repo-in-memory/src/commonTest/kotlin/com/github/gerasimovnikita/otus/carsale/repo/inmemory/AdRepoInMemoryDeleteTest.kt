package com.github.gerasimovnikita.otus.carsale.repo.inmemory

import com.github.gerasimovnikita.otus.carsale.repo.tests.RepoAdDeleteTest
import repo.IAdRepository


class AdRepoInMemoryDeleteTest : RepoAdDeleteTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
