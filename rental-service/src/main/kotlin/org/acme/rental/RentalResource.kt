package org.acme.rental

import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import java.time.LocalDate
import java.util.concurrent.atomic.AtomicLong

@Path("/rental")
class RentalResource {
    private val id = AtomicLong(0)

    @Path("/start/{userId}/{reservationId}")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    fun start(
        userId: String,
        reservationId: Long,
    ): Rental =
        Rental(
            id = id.incrementAndGet(),
            userId = userId,
            reservationId = reservationId,
            startDate = LocalDate.now(),
        )
}
