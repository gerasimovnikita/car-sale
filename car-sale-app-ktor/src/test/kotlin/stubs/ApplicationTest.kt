package stubs

import CarSaleAdStub
import CarSaleCorSettings
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import com.github.gerasimovnikita.otus.carsale.app.ktor.module
import com.github.gerasimovnikita.otus.carsale.repo.inmemory.AdRepoInMemory
import com.github.gersimovnikita.otus.carsale.app.common.CarSaleAppSettings
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.server.config.*
import io.ktor.server.testing.*
import models.CarSaleAdId
import models.CarSaleAdLock
import models.CarSaleAdStatus
import models.CarSaleAdVisibility
import org.junit.Test
import kotlin.test.assertEquals


class ApplicationTest {
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidSup = "10000000-0000-0000-0000-000000000003"
    private val createAd = AdCreateObject(
        carName = "Болт",
        description = "КРУТЕЙШИЙ",
        adStatus = AdStatus.ACTIVE,
        visibility = AdVisibility.PUBLIC,
    )
    private val requestCreateObj = AdCreateRequest(
        requestId = "12345",
        ad = createAd,
        debug = AdDebug(
            mode = AdRequestDebugMode.TEST,
        )
    )
    private val initAd = CarSaleAdStub.prepareResult{
        id = CarSaleAdId(uuidOld)
        carName = "Болт"
        description = "КРУТЕЙШИЙ"
        adStatus = CarSaleAdStatus.ACTIVE
        visibility = CarSaleAdVisibility.VISIBLE_PUBLIC
        lock = CarSaleAdLock(uuidOld)
    }
    private val initAdSupply = CarSaleAdStub.prepareResult{
        id = CarSaleAdId(uuidSup)
        carName = "Болт"
        description = "КРУТЕЙШИЙ"
        adStatus = CarSaleAdStatus.ACTIVE
        visibility = CarSaleAdVisibility.VISIBLE_PUBLIC
    }

    @Test
    fun createTest() = testApplication {
        val client = myClient()

        val response = client.post("/api/v1/car-sale-ad/create") {
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
                    stub = AdRequestDebugStubs.SUCCESS
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

        val repo = AdRepoInMemory(initObjects = listOf(initAd, initAdSupply), randomUuid = { uuidNew })
        val client = myClient()

        application {
            module(CarSaleAppSettings(corSettings = CarSaleCorSettings(repoTest = repo)))
        }
        environment {
            config = MapApplicationConfig()
        }

        val created = initObject(client)

        val adUpdate = AdUpdateObject(
            id = created.ad?.id,
            carName = "some new car",
            description = "new description",
            yearOfProduction = "2023",
            milage = "23",
            visibility = AdVisibility.PUBLIC,
            lock = created.ad?.lock,
            adStatus = AdStatus.ACTIVE
        )

        val response = client.post("/api/v1/car-sale-ad/update") {
            val requestObj = AdUpdateRequest(
                requestId = "12345",
                ad = adUpdate,
                debug = AdDebug(
                    mode = AdRequestDebugMode.STUB,
                    stub = AdRequestDebugStubs.SUCCESS
                ),
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(adUpdate.carName, responseObj.ad?.carName)
        assertEquals(adUpdate.description, responseObj.ad?.description)
        assertEquals(adUpdate.adStatus, responseObj.ad?.adStatus)
        assertEquals(adUpdate.visibility, responseObj.ad?.visibility)
    }

    private fun ApplicationTestBuilder.myClient() = createClient {
        install(ContentNegotiation) {
            jackson {
                disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)

                enable(SerializationFeature.INDENT_OUTPUT)
                writerWithDefaultPrettyPrinter()
            }
        }
    }

    private suspend fun initObject(client: HttpClient): AdCreateResponse {
        val responseCreate = client.post("/api/v1/car-sale-ad/create") {
            contentType(ContentType.Application.Json)
            setBody(requestCreateObj)
        }
        assertEquals(200, responseCreate.status.value)
        return responseCreate.body<AdCreateResponse>()
    }
}