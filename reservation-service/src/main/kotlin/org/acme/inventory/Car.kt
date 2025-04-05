package org.acme.inventory

data class Car(
    val id: Long = 0,
    val licensePlateNumber: String = "",
    val manufacturer: String = "",
    val model: String = "",
)