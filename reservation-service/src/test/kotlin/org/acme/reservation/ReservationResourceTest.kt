package org.acme.reservation

import io.quarkus.test.common.http.TestHTTPEndpoint
import io.quarkus.test.common.http.TestHTTPResource
import io.quarkus.test.junit.QuarkusTest
import io.restassured.RestAssured.given
import io.restassured.http.ContentType
import org.acme.rest.ReservationResource
import org.hamcrest.CoreMatchers.notNullValue
import org.junit.jupiter.api.Test
import java.net.URL
import java.time.LocalDate

@QuarkusTest
class ReservationResourceTest {
    @TestHTTPEndpoint(ReservationResource::class)
    @TestHTTPResource
    lateinit var reservationResource: URL

    @Test
    fun testReservationIds() {
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
}
