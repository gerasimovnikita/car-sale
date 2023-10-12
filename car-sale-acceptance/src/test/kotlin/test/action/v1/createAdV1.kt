package test.action.v1

import fixture.client.Client
import io.kotest.assertions.withClue
import io.kotest.matchers.should

suspend fun Client.createAd(): Unit =
    withClue("createAdV1"){
        val response = sendAndReceive(
            "ad/create", """
                {
                    "name": "Nikita"
                }
            """.trimIndent()
        )

        response should haveNoErrors
    }