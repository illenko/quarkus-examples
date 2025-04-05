package org.acme.inventory

import io.smallrye.graphql.client.typesafe.api.GraphQLClientApi
import org.eclipse.microprofile.graphql.Query

interface InventoryClient {
    fun allCars(): List<Car>
}

@GraphQLClientApi(configKey = "inventory")
interface GraphQLInventoryClient : InventoryClient {
    @Query("cars")
    override fun allCars(): List<Car>
}
