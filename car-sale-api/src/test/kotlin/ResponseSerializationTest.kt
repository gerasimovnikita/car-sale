import carsale.marketplace.api.apiMapper
import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import org.junit.Test
import kotlin.test.assertContains
import kotlin.test.assertEquals

class ResponseSerializationTest {
    private val response = AdCreateResponse(
        requestId = "123",
        ad = AdResponseObject(
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
        val json = apiMapper.writeValueAsString(response)

        assertContains(json, Regex("\"carName\":\\s*\"car\""))
        assertContains(json, Regex("\"responseType\":\\s*\"create\""))
    }

    @Test
    fun deserialize(){
        val json = apiMapper.writeValueAsString(response)
        val obj = apiMapper.readValue(json, IResponse::class.java) as AdCreateResponse

        assertEquals(response, obj)
    }
}