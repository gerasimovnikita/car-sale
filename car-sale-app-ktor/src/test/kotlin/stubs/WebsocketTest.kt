package stubs

import carsale.marketplace.api.apiMapper
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import io.ktor.client.plugins.websocket.*
import io.ktor.server.testing.*
import io.ktor.websocket.*
import kotlinx.coroutines.withTimeout
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

class WebsocketTest {
    @Test
    fun createStub(){
        val request = AdCreateRequest(
            requestId = "12345",
            ad = AdCreateObject(
                carName = "some car",
                description = "description",
                yearOfProduction = "2012",
                milage = "999999",
                visibility = AdVisibility.PUBLIC
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request){
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun readStub(){
        val request = AdReadRequest(
            requestId = "12345",
            ad = AdReadObject(
                "666"
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request){
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun updateStub(){
        val request = AdUpdateRequest(
            requestId = "12345",
            ad = AdUpdateObject(
                id="1",
                carName = "new some car",
                description = "new description",
                yearOfProduction = "2023",
                milage = "123",
                visibility = AdVisibility.PUBLIC
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request){
            assertEquals("12345", it.requestId)
        }
    }

    @Test
    fun deleteStub(){
        val request = AdDeleteRequest(
            requestId = "12345",
            ad = AdDeleteObject(
                id="1"
            ),
            debug = AdDebug(
                mode = AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
            )
        )

        testMethod<IResponse>(request){
            assertEquals("12345", it.requestId)
        }
    }

    private inline fun <reified T> testMethod(
        request: Any,
        crossinline assertBlock: (T) -> Unit
    ) = testApplication {
        val client = createClient {
            install(WebSockets)
        }

        client.webSocket("/ws/v1"){
            withTimeout(3000){
                val income = incoming.receive() as Frame.Text
                val response = apiMapper.readValue(income.readText(), T::class.java)
                assertIs<AdInitResponse>(response)
            }
            send(Frame.Text(apiMapper.writeValueAsString(request)))
            withTimeout(3000){
                val income = incoming.receive()
                val incomeAsText = income as Frame.Text
                val response = apiMapper.readValue(incomeAsText.readText(), T::class.java)
                assertBlock(response)
            }
        }
    }
}