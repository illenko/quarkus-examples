package org.acme.inventory.database

import io.quarkus.logging.Log
import jakarta.inject.Singleton
import org.acme.inventory.model.Car
import java.util.concurrent.CopyOnWriteArrayList
import java.util.concurrent.atomic.AtomicLong

@Singleton
class CarInventory {
    private val cars =
        CopyOnWriteArrayList<Car>().apply {
            Log.info("Initializing car inventory with sample data")
            addAll(
                listOf(
                    Car(
                        id = ids.incrementAndGet(),
                        licensePlateNumber = "ABC123",
                        manufacturer = "Toyota",
                        model = "Corolla",
                    ),
                    Car(
                        id = ids.incrementAndGet(),
                        licensePlateNumber = "XYZ789",
                        manufacturer = "Honda",
                        model = "Civic",
                    ),
                    Car(
                        id = ids.incrementAndGet(),
                        licensePlateNumber = "LMN456",
                        manufacturer = "Ford",
                        model = "Focus",
                    ),
                ),
            )
        }

    fun all(): MutableList<Car> = cars

    fun add(car: Car): Car {
        car.id = ids.incrementAndGet()
        cars.add(car)
        return car
    }

    fun remove(licensePlateNumber: String): Car {
        val car =
            cars.find { it.licensePlateNumber == licensePlateNumber }
                ?: throw IllegalArgumentException("Car with license plate number $licensePlateNumber not found")
        cars.remove(car)
        return car
    }

    companion object {
        val ids = AtomicLong(0)
    }
}
