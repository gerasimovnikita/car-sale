package com.github.gerasimovnikita.otus.carsale.repo.inmemory

import com.github.gerasimovnikita.otus.carsale.repo.tests.RepoAdUpdateTest
import repo.IAdRepository

class AdRepoInMemoryUpdateTest : RepoAdUpdateTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects,
    )
}
