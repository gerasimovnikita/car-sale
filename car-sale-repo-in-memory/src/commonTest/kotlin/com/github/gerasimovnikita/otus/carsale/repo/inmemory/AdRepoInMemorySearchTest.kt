package com.github.gerasimovnikita.otus.carsale.repo.inmemory

import com.github.gerasimovnikita.otus.carsale.repo.tests.RepoAdSearchTest
import repo.IAdRepository

class AdRepoInMemorySearchTest : RepoAdSearchTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
