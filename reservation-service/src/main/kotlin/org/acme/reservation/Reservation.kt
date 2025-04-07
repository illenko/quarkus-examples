package org.acme.reservation

import java.time.LocalDate

data class Reservation(
    val id: Long? = null,
    val carId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    var userId: String? = null,
) {
    fun isReserved(
        startDate: LocalDate,
        endDate: LocalDate,
    ): Boolean = !(startDate.isAfter(this.endDate) || endDate.isBefore(this.startDate))
}
