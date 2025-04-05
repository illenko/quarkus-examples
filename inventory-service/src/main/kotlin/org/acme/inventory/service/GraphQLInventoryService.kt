package org.acme.inventory.service

import io.quarkus.logging.Log
import org.acme.inventory.database.CarInventory
import org.acme.inventory.model.Car
import org.eclipse.microprofile.graphql.GraphQLApi
import org.eclipse.microprofile.graphql.Mutation
import org.eclipse.microprofile.graphql.Query

@GraphQLApi
class GraphQLInventoryService(
    private val inventory: CarInventory,
) {
    @Query
    fun cars(): List<Car> =
        inventory.all().also {
            Log.info("Cars fetched: $it")
        }

    @Mutation
    fun register(car: Car): Car =
        inventory
            .add(car)
            .also {
                Log.info("Car registered: $it")
            }

    @Mutation
    fun delete(licensePlateNumber: String): Boolean =
        (
            inventory
                .all()
                .find { it.licensePlateNumber == licensePlateNumber }
                ?.run {
                    inventory.all().remove(this)
                    true
                } ?: false
        ).also {
            Log.info("Car deleted: $it")
        }
}
