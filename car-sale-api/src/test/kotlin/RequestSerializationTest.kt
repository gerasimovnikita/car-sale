import carsale.marketplace.api.apiMapper
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class RequestSerializationTest {
    var req = AdCreateRequest(
        requestId = "1234",
        debug = AdDebug(
            mode= AdRequestDebugMode.STUB,
            stub = AdRequestDebugStubs.BAD_DESCRIPTION
        ),
        ad = AdCreateObject(
            carName = "car",
            description = "some description",
            yearOfProduction = "2012",
            milage = "99999",
            adStatus = AdStatus.ACTIVE,
            visibility = AdVisibility.PUBLIC
        )
    )

    @Test
    fun serialize(){
        val json = apiMapper.writeValueAsString(req)
        assertContains(json, Regex("\"carName\":\\s*\"car\""))
        assertContains(json, Regex("\"mode\":\\s*\"stub\""))
        assertContains(json, Regex("\"stub\":\\s*\"badDescription\""))
        assertContains(json, Regex("\"requestType\":\\s*\"create\""))
    }

    @Test
    fun deserialize(){
        val json = apiMapper.writeValueAsString(req)
        val obj = apiMapper.readValue(json, IRequest::class.java) as AdCreateRequest

        assertEquals(req, obj)
    }

    @Test
    fun deserializeNaked(){
        val jsonString = """
            {"requestId": "123"}
        """.trimIndent()
        val obj = apiMapper.readValue(jsonString, AdCreateRequest::class.java)

        assertEquals("123", obj.requestId)
    }
}