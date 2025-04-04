package org.acme.inventory.database

import jakarta.enterprise.context.ApplicationScoped
import org.acme.inventory.model.Car
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

@ApplicationScoped
class CarInventory {
    private val cars =
        CopyOnWriteArrayList<Car>().apply {
            addAll(
                listOf(
                    Car(
                        licensePlateNumber = "ABC123",
                        manufacturer = "Toyota",
                        model = "Corolla",
                    ).apply {
                        id = ids.incrementAndGet()
                    },
                    Car(
                        licensePlateNumber = "XYZ789",
                        manufacturer = "Honda",
                        model = "Civic",
                    ).apply {
                        id = ids.incrementAndGet()
                    },
                    Car(
                        licensePlateNumber = "LMN456",
                        manufacturer = "Ford",
                        model = "Focus",
                    ).apply {
                        id = ids.incrementAndGet()
                    },
                ),
            )
        }

    fun getCars(): MutableList<Car> = cars

    companion object {
        val ids = AtomicLong(0)
    }
}
