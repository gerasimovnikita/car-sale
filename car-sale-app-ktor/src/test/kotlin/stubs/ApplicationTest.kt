package stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals


class ApplicationTest {
    @Test
    fun createTest() = testApplication {
        val client = myClient()

        val response = client.post("/api/v1/car-sale-ad/create"){
            val requestObj = AdCreateRequest(
                requestId = "12345",
                ad = AdCreateObject(
                    carName = "some car",
                    description = "description",
                    yearOfProduction = "2012",
                    milage = "99999",
                    visibility = AdVisibility.PUBLIC
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub=AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdCreateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
        assertEquals("111", responseObj.ad?.id)
    }

    @Test
    fun updateTest() = testApplication {
        val client = myClient()

        val response = client.post("/api/v1/car-sale-ad/update"){
            val requestObj = AdUpdateRequest(
                requestId = "12345",
                ad = AdUpdateObject(
                    id = "111",
                    carName = "some new car",
                    description = "new description",
                    yearOfProduction = "2023",
                    milage = "23",
                    visibility = AdVisibility.PUBLIC
                ),
                debug = AdDebug(
                    mode=AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }

        val responseObj = response.body<AdUpdateResponse>()
        println(responseObj)
        assertEquals(200, response.status.value)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation){
            jackson{
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }
}