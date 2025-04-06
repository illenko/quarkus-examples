package org.acme.reservation

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.common.http.TestHTTPResource
import io.quarkus.test.junit.DisabledOnIntegrationTest
import io.quarkus.test.junit.QuarkusMock
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.acme.inventory.Car
import org.acme.inventory.GraphQLInventoryClient
import org.acme.rest.ReservationResource
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.Matchers.hasSize
import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import java.net.URL
import java.time.LocalDate

@QuarkusTest
class ReservationResourceTest {
    @TestHTTPEndpoint(ReservationResource::class)
    @TestHTTPResource
    lateinit var reservationResource: URL

    @Test
    fun `test reservation`() {
        val reservation =
            Reservation(
                carId = 12345L,
                startDate =
                    LocalDate
                        .parse("2025-03-20"),
                endDate =
                    LocalDate
                        .parse("2025-03-29"),
            )

        given()
            .contentType(ContentType.JSON)
            .body(reservation)
            .`when`()
            .post(reservationResource)
            .then()
            .statusCode(200)
            .body("id", notNullValue())
    }

    @TestHTTPEndpoint(ReservationResource::class)
    @TestHTTPResource("availability")
    lateinit var availability: URL

    @Test
    @DisabledOnIntegrationTest(forArtifactTypes = [DisabledOnIntegrationTest.ArtifactType.NATIVE_BINARY])
    fun `test making reservation with availability check`() {
        val inventoryClientMock = mock(GraphQLInventoryClient::class.java)
        val peugeot =
            Car(
                id = 1L,
                licensePlateNumber = "ABC123",
                manufacturer = "Peugeot",
                model = "406",
            )

        `when`(inventoryClientMock.allCars()).thenReturn(listOf(peugeot))

        QuarkusMock.installMockForType(
            inventoryClientMock,
            GraphQLInventoryClient::class.java,
        )

        val startDate = "2022-01-01"
        val endDate = "2022-01-10"

        val cars =
            given()
                .queryParam("startDate", startDate)
                .queryParam("endDate", endDate)
                .`when`()
                .get(availability)
                .then()
                .statusCode(200)
                .extract()
                .`as`(Array<Car>::class.java)

        val car = cars.first()

        val reservation =
            Reservation(
                carId = car.id,
                startDate = LocalDate.parse(startDate),
                endDate = LocalDate.parse(endDate),
            )

        given()
            .contentType(ContentType.JSON)
            .body(reservation)
            .`when`()
            .post(reservationResource)
            .then()
            .statusCode(200)
            .body("carId", `is`(car.id.toInt()))

        given()
            .queryParam("startDate", startDate)
            .queryParam("endDate", endDate)
            .`when`()
            .get(availability)
            .then()
            .statusCode(200)
            .body("findAll { car -> car.id == ${car.id}}", hasSize<Car>(0))
    }
}
