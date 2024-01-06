package com.github.gerasimovnikita.otus.carsale.repo.inmemory

import com.github.gerasimovnikita.otus.carsale.repo.tests.RepoAdCreateTest


class AdRepoInMemoryCreateTest : RepoAdCreateTest() {
    override val repo = AdRepoInMemory(
        initObjects = initObjects,
    )
}