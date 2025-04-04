package org.acme.inventory.service

import org.acme.inventory.database.CarInventory
import org.acme.inventory.database.CarInventory.Companion.ids
import org.acme.inventory.model.Car
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query

@GraphQLApi
class GraphQLInventoryService(
    private val carInventory: CarInventory,
) {
    @Query
    fun cars(): List<Car> = carInventory.getCars()

    @Mutation
    fun addCar(car: Car): Car {
        car.id = ids.getAndIncrement()
        carInventory.getCars().add(car)
        return car
    }

    @Mutation
    fun deleteCar(licensePlateNumber: String): Boolean =
        carInventory
            .getCars()
            .find { it.licensePlateNumber == licensePlateNumber }
            ?.run {
                carInventory.getCars().remove(this)
                true
            } ?: false
}
