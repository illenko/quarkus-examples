package org.acme.reservation

import io.quarkus.test.junit.QuarkusTest
import jakarta.inject.Inject
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.LocalDate

@QuarkusTest
class ReservationRepositoryTest {
    @Inject
    lateinit var reservationRepository: ReservationRepository

    @Test
    fun `create reservation`() {
        val reservation =
            reservationRepository.save(
                Reservation(
                    carId = 1,
                    startDate = LocalDate.now().plusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                ),
            )

        assertNotNull(reservation.id)
        assertTrue(reservationRepository.findAll().contains(reservation))
    }
}
