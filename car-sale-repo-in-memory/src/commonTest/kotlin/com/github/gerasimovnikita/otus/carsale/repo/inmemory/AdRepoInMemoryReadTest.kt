package com.github.gerasimovnikita.otus.carsale.repo.inmemory

import com.github.gerasimovnikita.otus.carsale.repo.tests.RepoAdReadTest
import repo.IAdRepository


class AdRepoInMemoryReadTest: RepoAdReadTest() {
    override val repo: IAdRepository = AdRepoInMemory(
        initObjects = initObjects
    )
}
