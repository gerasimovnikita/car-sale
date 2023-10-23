import com.github.gerasimovnikita.otus.`car-sale`.api.v1.models.AdCreateObject
import com.github.gerasimovnikita.otus.car.*
import models.CarSaleAdStatus
import models.CarSaleAdVisibility
import models.CarSaleWorkMode
import org.junit.Test
import stubs.CarSaleStubs
import kotlin.test.assertEquals

class MappersTest {
    @Test
    fun fromTransport(){
        var req = AdCreateRequest(
            requestId = "1234",
            debug = AdDebug(
                mode=AdRequestDebugMode.STUB,
                stub = AdRequestDebugStubs.SUCCESS
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

        val context = CarSaleContext()
        context.fromTransport(req)

        assertEquals(CarSaleStubs.SUCCESS, context.stubCase)
        assertEquals(CarSaleWorkMode.STUB, context.workMode)
        assertEquals("car", context.carSaleRequest.carName)
        assertEquals(CarSaleAdVisibility.VISIBLE_PUBLIC, context.carSaleRequest.visibility)
        assertEquals(CarSaleAdStatus.ACTIVE, context.carSaleRequest.adStatus)
        assertEquals("1234", context.carSaleRequest.id.asString())
        assertEquals("some description", context.carSaleRequest.description)
        assertEquals(2012, context.carSaleRequest.yearOfProduction)
        assertEquals(99999, context.carSaleRequest.milage)

    }
}