package org.acme.users.model

import java.time.LocalDate

data class Reservation(
    val id: Long,
    val carId: Long,
    val startDate: LocalDate,
    val endDate: LocalDate,
    var userId: String,
) 
