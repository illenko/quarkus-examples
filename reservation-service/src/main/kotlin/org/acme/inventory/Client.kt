package org.acme.inventory

import jakarta.inject.Singleton

interface InventoryClient {
    fun allCars(): List<Car>
}

@Singleton
class InMemoryInventoryClient : InventoryClient {
    private val cars =
        listOf(
            Car(1, "ABC123", "Toyota", "Corolla"),
            Car(2, "XYZ789", "Honda", "Civic"),
            Car(3, "LMN456", "Ford", "Focus"),
        )

    override fun allCars(): List<Car> = cars
}
