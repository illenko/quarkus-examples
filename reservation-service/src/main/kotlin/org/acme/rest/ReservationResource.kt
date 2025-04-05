package org.acme.rest

import io.quarkus.logging.Log
import io.smallrye.graphql.client.GraphQLClient
import jakarta.ws.rs.GET
import jakarta.ws.rs.POST
import jakarta.ws.rs.Path
import jakarta.ws.rs.Produces
import jakarta.ws.rs.core.MediaType
import org.acme.inventory.Car
import org.acme.inventory.GraphQLInventoryClient
import org.acme.rental.RentalClient
import org.acme.reservation.Reservation
import org.acme.reservation.ReservationRepository
import org.eclipse.microprofile.rest.client.inject.RestClient
import org.jboss.resteasy.reactive.RestQuery
import java.time.LocalDate

@Path("reservations")
@Produces(MediaType.APPLICATION_JSON)
class ReservationResource(
    private val reservationRepository: ReservationRepository,
    @GraphQLClient("inventory") private val inventoryClient: GraphQLInventoryClient,
    @RestClient private val rentalClient: RentalClient,
) {
    @GET
    @Path("availability")
    fun availability(
        @RestQuery startDate: LocalDate? = LocalDate.now(),
        @RestQuery endDate: LocalDate? = startDate?.plusDays(1),
    ): List<Car> {
        val carById = inventoryClient.allCars().associateBy { it.id }.toMutableMap()

        Log.info("Cars available: $carById")

        reservationRepository
            .findAll()
            .filter { it.isReserved(startDate!!, endDate!!) }
            .mapNotNull { carById.remove(it.carId) }

        return carById.values.toList()
    }

    @POST
    fun reserve(reservation: Reservation): Reservation {
        val result = reservationRepository.save(reservation)

        val userId = "x"

        if (result.startDate == LocalDate.now()) {
            val rental =
                rentalClient.start(
                    userId = userId,
                    reservationId = result.id,
                )

            Log.info("Rental started: $rental")
        }

        return result
    }
}
