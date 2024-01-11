package stubs

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.SerializationFeature
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.http.*
import io.ktor.serialization.jackson.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.server.testing.*
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class V1AdInmemoryApiTest {
    private val createAd = AdCreateObject(
        carName = "Болт",
        description = "КРУТЕЙШИЙ",
        visibility = AdVisibility.PUBLIC,
    )
    private val requestCreateObj = AdCreateRequest(
        requestId = "12345",
        ad = createAd,
        debug = AdDebug(
            mode = AdRequestDebugMode.TEST,
        )
    )

    @Test
    fun create() = testApplication {
        val client = myClient()
        val responseObj = initObject(client)
        assertEquals(createAd.carName, responseObj.ad?.carName)
        assertEquals(createAd.description, responseObj.ad?.description)
        assertEquals(createAd.visibility, responseObj.ad?.visibility)
    }

    @Test
    fun read() = testApplication {
        val client = myClient()
        val id = initObject(client).ad?.id
        val response = client.post("/api/v1/car-sale-ad/read") {
            val requestObj = AdReadRequest(
                requestId = "12345",
                ad = AdReadObject(id),
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdReadResponse>()
        assertEquals(200, response.status.value)
        assertEquals(id, responseObj.ad?.id)
    }

    @Test
    fun update() = testApplication {
        val client = myClient()

        val created = initObject(client)

        val adUpdate = AdUpdateObject(
            id = created.ad?.id,
            carName = "Болт",
            description = "КРУТЕЙШИЙ",
            visibility = AdVisibility.PUBLIC,
            lock = created.ad?.lock,
        )

        val response = client.post("/api/v1/car-sale-ad/update") {
            val requestObj = AdUpdateRequest(
                requestId = "12345",
                ad = adUpdate,
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdUpdateResponse>()
        assertEquals(200, response.status.value)
        assertEquals(adUpdate.id, responseObj.ad?.id)
        assertEquals(adUpdate.carName, responseObj.ad?.carName)
        assertEquals(adUpdate.description, responseObj.ad?.description)
        assertEquals(adUpdate.carName, responseObj.ad?.carName)
        assertEquals(adUpdate.visibility, responseObj.ad?.visibility)
    }

    @Test
    fun delete() = testApplication {
        val client = myClient()
        val created = initObject(client)

        val response = client.post("/api/v1/car-sale-ad/delete") {
            val requestObj = AdDeleteRequest(
                requestId = "12345",
                ad = AdDeleteObject(
                    id = created.ad?.id,
                    lock = created.ad?.lock
                ),
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdDeleteResponse>()
        assertEquals(200, response.status.value)
        assertEquals(created.ad?.id, responseObj.ad?.id)
    }

    @Test
    fun search() = testApplication {
        val client = myClient()
        val initObject = initObject(client)
        val response = client.post("/api/v1/car-sale-ad/search") {
            val requestObj = AdSearchRequest(
                requestId = "12345",
                adFilter = AdSearchFilter(),
                debug = AdDebug(
                    mode = AdRequestDebugMode.TEST,
                )
            )
            contentType(ContentType.Application.Json)
            setBody(requestObj)
        }
        val responseObj = response.body<AdSearchResponse>()
        assertEquals(200, response.status.value)
        assertNotEquals(0, responseObj.ads?.size)
        assertEquals(initObject.ad?.id, responseObj.ads?.first()?.id)
    }

      private suspend fun initObject(client: HttpClient): AdCreateResponse {
        val responseCreate = client.post("/api/v1/car-sale-ad/create") {
            contentType(ContentType.Application.Json)
            setBody(requestCreateObj)
        }
        assertEquals(200, responseCreate.status.value)
        return responseCreate.body<AdCreateResponse>()
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
}


/*class ApplicationTest {
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

//        val repo = AdRepoInMemory(initObjects = listOf(initAd, initAdSupply), randomUuid = { uuidNew })
        val repo = RepoAdSQL(initObjects = listOf(initAd, initAdSupply), randomUuid = { uuidNew })
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
}*/