package org.acme.reservation

import java.time.LocalDate

data class Reservation(
    val id: Long,
    val carId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
) {
    fun isReserved(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean = !(startDate.isAfter(this.endDate) || endDate.isBefore(this.startDate))
}
