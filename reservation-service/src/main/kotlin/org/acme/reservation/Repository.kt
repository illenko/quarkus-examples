package org.acme.reservation

import jakarta.inject.Singleton
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

interface ReservationRepository {
    fun findAll(): List<Reservation>

    fun save(reservation: Reservation): Reservation
}

@Singleton
class InMemoryReservationRepository : ReservationRepository {
    private val ids = AtomicLong(0)
    private val store = CopyOnWriteArrayList<Reservation>()

    override fun findAll(): List<Reservation> = store.toList()

    override fun save(reservation: Reservation): Reservation {
        val newReservation = reservation.copy(id = ids.incrementAndGet())
        store.add(newReservation)
        return newReservation
    }
}
