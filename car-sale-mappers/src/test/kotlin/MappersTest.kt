import com.github.gerasimovnikita.otus.carsale.api.v1.models.*
import models.*
import org.junit.Test
import stubs.CarSaleStubs
import kotlin.test.assertEquals

class MappersTest {
    @Test
    fun fromTransport(){
        val req = AdCreateRequest(
            requestId = "1234",
            debug = AdDebug(
                mode= AdRequestDebugMode.STUB,
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
        assertEquals("1234", context.requestID.asString())
        assertEquals("some description", context.carSaleRequest.description)
        assertEquals(2012, context.carSaleRequest.yearOfProduction)
        assertEquals(99999, context.carSaleRequest.milage)

    }

    @Test
    fun toTransport(){
        val context = CarSaleContext(
            command = CarSaleCommand.CREATE,
            state = CarSaleState.RUNNING,
            requestID = CarSaleRequestId("1234"),
            carSaleResponse = CarSaleAd(
                carName = "car",
                description = "some description",
                yearOfProduction = 2012,
                milage = 99999,
                adStatus = CarSaleAdStatus.ACTIVE,
                visibility = CarSaleAdVisibility.VISIBLE_PUBLIC
            ),
            errors = mutableListOf(
                CarSaleError(
                    code = "error",
                    group = "request",
                    field = "carName",
                    message = "invalid car name"
                )
            )
        )

        val req = context.toTransport() as AdCreateResponse
        assertEquals("1234", req.requestId)
        assertEquals("car", req.ad?.carName)
        assertEquals("some description", req.ad?.description)
        assertEquals("2012", req.ad?.yearOfProduction)
        assertEquals("99999", req.ad?.milage)
        assertEquals(AdStatus.ACTIVE, req.ad?.adStatus)
        assertEquals(AdVisibility.PUBLIC, req.ad?.visibility)
        assertEquals(1, req.errors?.size)
        assertEquals("error", req.errors?.firstOrNull()?.code)
        assertEquals("request", req.errors?.firstOrNull()?.group)
        assertEquals("carName", req.errors?.firstOrNull()?.field)
        assertEquals("invalid car name", req.errors?.firstOrNull()?.message)
    }
}